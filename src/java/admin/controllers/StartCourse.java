/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Course;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/courses/startcourse")
public class StartCourse extends Controller {

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int courseId = Integer.parseInt(req.getParameter("courseId"));

        Course course = (Course) session.get(Course.class, courseId);
        course.startCourse();

        resp.sendRedirect("/OAS/admin/courses/detailcourse?courseId=" + courseId);
    }

}
