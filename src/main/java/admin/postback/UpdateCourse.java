/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.Course;
import entities.CriteriaHolder;
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

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/courses/update-course")
public class UpdateCourse extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var courseIdString = req.getParameter("courseId");
        String name = req.getParameter("courseName");

        if (StringUtils.isAnyBlank(name, courseIdString)) {
            onError(req, resp);
            return;
        }

        long courseId = Long.parseLong(courseIdString);

        var holder = CriteriaHolder.getUpdateHolder(session, Course.class);

        Course course = session.get(Course.class, courseId);
        course.setName(name);
        session.update(course);

        UrlParameters parameters = new UrlParameters()
                .addSuccessParameter()
                .addParameter("courseId", String.valueOf(courseId))
                .addMessage("Course name updated");

        resp.sendRedirect(
                parameters.getUrl("/OAS/admin/courses/course-details")
        );

    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var url = new UrlParameters()
                .addErrorParameter();
        var courseId = req.getParameter("courseId");

        if (StringUtils.isBlank(courseId)) {
            url.addMessage("The course you are tying to update does not exist");
            resp.sendRedirect(url.getUrl("/OAS/admin/courses"));
        }else{
            url.addMessage("Please provide the necessary data");
        }

        resp.sendRedirect(
                new UrlParameters()
                        .addErrorParameter()
                        .addParameter("courseId", req.getParameter("courseId"))
                        .addMessage("Unable to edit the course as details were missing")
                        .getUrl("/OAS/admin/courses")
        );
    }
}
