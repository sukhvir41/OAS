/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.CriteriaHolder;
import utility.PostBackController;
import utility.UrlBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/subjects/update-subject")
public class UpdateSubject extends PostBackController {


    // todo: have to make this transaction serializable.
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var subjectIdString = req.getParameter("subjectId");
        var subjectName = req.getParameter("subjectName");
        var electiveString = req.getParameter("elective");


        if (StringUtils.isAnyBlank(subjectIdString, subjectName, electiveString)) {
            onError(req, resp);
            return;
        }

        long subjectId = Long.parseLong(subjectIdString);
        boolean elective = Boolean.parseBoolean(electiveString);
        List<String> classroomIds = Arrays.asList(req.getParameterValues("classrooms"));

        var subject = EntityHelper.getInstance(subjectId, Subject_.id, Subject.class, session, false, Subject_.COURSE);

        var classrooms = getClassrooms(session, classroomIds);


        // check if the classrooms are in the course selected if all of the classrooms are in the same course then continue.
        var count = classrooms.stream()
                .filter(classRoom -> classRoom.getCourse().equals(subject.getCourse()))
                .count();

        if (classrooms.size() != count) {
            onError(req, resp);
            return;
        }

        // delete all classroom subject link fot the subject specified
        var deleteHolder = CriteriaHolder.getDeleteHolder(session, SubjectClassRoomLink.class);

        deleteHolder.getQuery().where(
                deleteHolder.getBuilder()
                        .equal(deleteHolder.getRoot().get(SubjectClassRoomLink_.subject), subject)
        );

        session.createQuery(deleteHolder.getQuery())
                .executeUpdate();

        subject.setName(subjectName);
        subject.setElective(elective);

        classrooms.stream()
                .map(classRoom -> new SubjectClassRoomLink(subject, classRoom))
                .forEach(session::save);

        var redirectUrl = new UrlBuilder()
                .addSuccessParameter()
                .addParameter("subjectId", subjectId)
                .addMessage("The subject : " + subjectName + " was updated");

        redirect(req, resp, redirectUrl.getUrl("/OAS/admin/subjects/subject-details"));
    }

    private void redirect(HttpServletRequest req, HttpServletResponse resp, String url) throws IOException {
        resp.sendRedirect(url);
    }


    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var redirectUrl = new UrlBuilder()
                .addErrorParameter();
        var subjectId = req.getParameter("subjectId");

        if (StringUtils.isBlank(subjectId)) {
            redirectUrl.addParameter("subjectId", subjectId)
                    .addMessage("Please provide necessary data to update the subject");
            redirect(req, resp, redirectUrl.getUrl("/OAS/admin/subjects/edit-subject"));
        } else {
            redirectUrl.addMessage("The subject you are trying to edit does not exist");
            redirect(req, resp, redirectUrl.getUrl("/OAS/admin/subjects"));
        }
    }


    private List<ClassRoom> getClassrooms(Session session, List<String> classrooms) {

        var holder = CriteriaHolder.getQueryHolder(session, ClassRoom.class);

        var graph = session.createEntityGraph(ClassRoom.class);
        graph.addAttributeNodes(ClassRoom_.COURSE);

        holder.getQuery().where(
                holder.getRoot().get(ClassRoom_.id).in(classrooms)
        );

        return session.createQuery(holder.getQuery())
                .setReadOnly(true)
                .getResultList();
    }

}



