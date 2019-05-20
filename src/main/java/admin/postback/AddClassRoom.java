/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.ClassRoom;
import entities.Course;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.PostBackController;
import utility.UrlParameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/classrooms/add-classroom")
public class AddClassRoom extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var theCourseId = req.getParameter("courseId");
        var name = req.getParameter("classRoomName");
        var division = req.getParameter("division");
        var theSemester = req.getParameter("semester");
        var theMinimumSubjects = req.getParameter("minimumSubjects");

        UrlParameters parameters = new UrlParameters();

        if (StringUtils.isAnyBlank(theCourseId, name, division, theSemester, theMinimumSubjects)) {
            parameters.addErrorParameter()
                    .addMessage("Please provide the correct details");

            redirect(req, resp, parameters);
            return;
        }

        long courseId = Long.parseLong(theCourseId);
        int semester = Integer.parseInt(theSemester);
        int minimumSubjects = Integer.parseInt(theMinimumSubjects);

        ClassRoom classRoom = new ClassRoom(name, division, semester, minimumSubjects);
        Course course = session.getReference(Course.class, courseId);
        classRoom.addCourse(course);
        session.save(classRoom);

        parameters.addSuccessParameter()
                .addMessage("Class room was added");

        redirect(req, resp, parameters);
    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UrlParameters parameters = new UrlParameters()
                .addErrorParameter()
                .addMessage("Please provide the correct details");

        redirect(req, resp, parameters);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, UrlParameters urlParameters) throws IOException {
        var from = Optional.of(request.getParameter("from"))
                .orElse("");
        var courseId = request.getParameter("courseId");

        switch (from) {
            case "course-details": {
                if (StringUtils.isBlank(courseId)) {
                    response.sendRedirect("/OAS/admin/subjects");
                } else {
                    response.sendRedirect(
                            urlParameters.addParamter("courseId", courseId)
                                    .getUrl("/OAS/admin/courses/course-details")
                    );
                }
                break;
            }
            default: {
                response.sendRedirect("/OAS/admin/subjects");
            }
        }
    }

}
