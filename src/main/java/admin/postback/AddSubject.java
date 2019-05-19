/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.criteria.Expression;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

import oshi.jna.platform.unix.CLibrary;
import utility.PostBackController;
import utility.UrlParameters;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/subjects/add-subject")
public class AddSubject extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        String name = req.getParameter("subjectName");
        boolean elective = Boolean.parseBoolean(req.getParameter("elective"));
        long courseId = Long.parseLong(req.getParameter("courseId"));
        List<Long> classRoomsIds = Arrays.stream(
                Optional.ofNullable(req.getParameterValues("classRooms"))
                        .orElse(new String[]{})
        )
                .map(Long::parseLong)
                .collect(Collectors.toList());

        Course course = session.get(Course.class, courseId);

        Subject subject = new Subject(name, elective, course);
        session.save(subject);

        // adding subjects to classrooms only if there are any as it optional
        if (!classRoomsIds.isEmpty()) {
            // get all the class rooms with the subjects and then add the subject to them
            var criteriaBuilder = session.getCriteriaBuilder();
            var classRoomQuery = criteriaBuilder.createQuery(ClassRoom.class);
            var classRoomRoot = classRoomQuery.from(ClassRoom.class);

            var classRoomGraph = session.createEntityGraph(ClassRoom.class);
            classRoomGraph.addAttributeNodes("subjects");


            classRoomQuery.where(classRoomRoot.get(ClassRoom_.id).in(classRoomsIds));

            session.createQuery(classRoomQuery)
                    .applyLoadGraph(classRoomGraph)
                    .getResultStream()
                    .forEach(classRoom -> classRoom.addSubject(subject));
        }

        UrlParameters parameters = new UrlParameters()
                .addSuccessParameter()
                .addMessage("Subject added to the course and the class room(s) specified");

        redirect(req, resp, parameters);
    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var parameters = new UrlParameters()
                .addErrorParameter()
                .addMessage("Please provide the correct parameters");

        redirect(req, resp, parameters);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, UrlParameters urlParameters) throws IOException {
        var from = request.getParameter("from");
        var courseId = request.getParameter("courseId");
        List<String> classRooms = Arrays.asList(
                Optional.ofNullable(request.getParameterValues("classes"))
                        .orElse(new String[]{})
        );

        switch (from) {
            case "course-details": {

                String url = Optional.ofNullable(courseId)
                        .map(id -> urlParameters.addParamter("courseId", courseId).getUrl("/OAS/admin/courses/course-details"))
                        .orElse(urlParameters.getUrl("/OAS/admin/subjects"));

                response.sendRedirect(url);
                break;

            }
            default: {
                response.sendRedirect(urlParameters.getUrl("/OAS/admin/subjects"));
            }
        }
    }


}
