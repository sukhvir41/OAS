/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.Controller;
import utility.UrlParameters;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/subjects/subject-details")
public class SubjectDetails extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var subjectIdString = req.getParameter("subjectId");

        var urlParameters = new UrlParameters();

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


        var classRooms = subject.getClassRooms()
                .parallelStream()
                .map(SubjectClassRoomLink::getClassRoom)
                .sorted((o1, o2) -> StringUtils.compare(o1.getName(), o2.getName()))
                .collect(Collectors.toList());

        if (Objects.isNull(subject)) {
            resp.sendRedirect(
                    urlParameters.addErrorParameter()
                            .addMessage("The subject you are trying to access does not exist")
                            .getUrl("/OAS/admin/subjects")
            );
            return;
        } else {
            req.setAttribute("classrooms", classRooms);
            req.setAttribute("subject", subject);

            req.getRequestDispatcher("/WEB-INF/admin/subject-details.jsp")
                    .include(req, resp);
        }
    }
}
