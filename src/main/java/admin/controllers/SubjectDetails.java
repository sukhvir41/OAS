/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.Controller;
import utility.CriteriaHolder;
import utility.UrlBuilder;

import javax.persistence.criteria.JoinType;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/subjects/subject-details")
public class SubjectDetails extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var subjectIdString = req.getParameter("subjectId");

        var urlParameters = new UrlBuilder();

        if (StringUtils.isBlank(subjectIdString)) {
            resp.sendRedirect(
                    urlParameters
                            .addErrorParameter()
                            .addMessage("The subject you are trying to access does not exist")
                            .getUrl("/OAS/admin/subjects")
            );
            return;
        }

        Long subjectId = Long.parseLong(subjectIdString);

        var subjectGraph = session.createEntityGraph(Subject.class);
        subjectGraph.addAttributeNodes(Subject_.COURSE);

        var classRoomSubjectLinkSubGraph = subjectGraph.addSubGraph(Subject_.classRooms);
        classRoomSubjectLinkSubGraph.addAttributeNodes(SubjectClassRoomLink_.CLASS_ROOM);

        var subject = EntityHelper.getInstance(subjectId, Subject_.id, Subject.class, session, true, subjectGraph);

        if (Objects.isNull(subject)) {
            resp.sendRedirect(
                    urlParameters.addErrorParameter()
                            .addMessage("The subject you are trying to access does not exist")
                            .getUrl("/OAS/admin/subjects")
            );
            return;
        }

        var classRooms = getCourseClassRooms(subject.getCourse(), session);

        var subjectClassRooms = subject.getClassRooms()
                .parallelStream()
                .map(SubjectClassRoomLink::getClassRoom)
                .sorted((o1, o2) -> StringUtils.compare(o1.getName(), o2.getName()))
                .collect(Collectors.toList());

        var canDelete = canDeleteSubject(subject, session);

        req.setAttribute("subjectClassrooms", subjectClassRooms);
        req.setAttribute("subject", subject);
        req.setAttribute("course", subject.getCourse());
        req.setAttribute("canDelete", canDelete);
        req.setAttribute("classRooms", classRooms);

        req.getRequestDispatcher("/WEB-INF/admin/subject-details.jsp")
                .include(req, resp);

    }

    private List<ClassRoom> getCourseClassRooms(Course course, Session session) {

        var holder = CriteriaHolder.getQueryHolder(session, ClassRoom.class);

        var courseJoin = holder.getRoot().join(ClassRoom_.course, JoinType.INNER);

        holder.getQuery()
                .where(
                        holder.getBuilder().equal(courseJoin.get(Course_.id), course.getId())
                );

        return session.createQuery(holder.getQuery())
                .setReadOnly(true)
                .getResultList();

    }

    private boolean canDeleteSubject(Subject subject, Session session) {

        var holder = CriteriaHolder.getQueryHolder(session, Long.class, Subject.class);

        holder.getRoot().join(Subject_.classRooms, JoinType.INNER);
        holder.getRoot().join(Subject_.students, JoinType.INNER);

        holder.getQuery()
                .where(
                        holder.getBuilder().equal(holder.getRoot().get(Subject_.id), subject.getId())
                );

        holder.getQuery().select(holder.getBuilder().count(holder.getRoot().get(Subject_.id)));

        return session.createQuery(holder.getQuery())
                .setMaxResults(1)
                .setReadOnly(true)
                .getSingleResult() < 1;

    }
}
