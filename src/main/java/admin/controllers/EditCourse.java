/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Course;
import entities.Course_;
import entities.EntityHelper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.Controller;
import utility.UrlParameters;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/courses/edit-course")
public class EditCourse extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var courseIdString = req.getParameter("courseId");

        if (StringUtils.isBlank(courseIdString)) {
            resp.sendRedirect(
                    new UrlParameters().addErrorParameter()
                            .addMessage("The course you are trying to access does not exist")
                            .getUrl("/OAS/admin/courses")
            );
            return;
        }

        long courseId = Long.parseLong(courseIdString);

        Course course = EntityHelper.getInstance(courseId, Course_.id, Course.class, session, true);

        req.setAttribute("course", course);
        req.getRequestDispatcher("/WEB-INF/admin/edit-course.jsp")
                .include(req, resp);

    }

}
