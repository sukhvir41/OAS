/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.Course;
import entities.Course_;
import entities.EntityHelper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.PostBackController;
import utility.UrlBuilder;

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
@WebServlet(urlPatterns = "/admin/courses/delete-course")
public class DeleteCourse extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        String courseId = req.getParameter("courseId");

        // ending the processing as the input validation failed
        if (!validate(req, resp)) {
            return;
        }

        UrlBuilder parameters = new UrlBuilder();

        long theCourseId = Long.parseLong(courseId);
        //this delete query will fail if the course has links to subject to classroom
        EntityHelper.deleteInstance(theCourseId, Course_.id, Course.class, session);

        parameters.addSuccessParameter()
                .addMessage("the course was deleted");
        resp.sendRedirect(parameters.getUrl("/OAS/admin/courses"));
    }


    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseId = req.getParameter("courseId");

        // ending the processing as the input validation failed
        if (!validate(req, resp)) {
            return;
        }

        UrlBuilder parameters = new UrlBuilder()
                .addErrorParameter()
                .addMessage("Before deleting this Course make sure that all of its subjects or class rooms are deleted or moved to another course")
                .addParameter("courseId", courseId);

        resp.sendRedirect(parameters.getUrl("/OAS/admin/courses/course-details"));
    }


    private boolean validate(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UrlBuilder parameters = new UrlBuilder();

        String courseId = request.getParameter("courseId");

        if (StringUtils.isBlank(courseId)) {
            parameters.addErrorParameter()
                    .addMessage("Could not find the course");

            response.sendRedirect(parameters.getUrl("/OAS/admin/courses"));
            return false;
        }

        return true;
    }

}
