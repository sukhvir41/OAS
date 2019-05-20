/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Course;
import org.hibernate.Session;
import utility.Controller;

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
        long courseId = Long.parseLong(req.getParameter("courseId"));

        Course course = session.get(Course.class, courseId);

        req.setAttribute("course", course);
        req.getRequestDispatcher("/WEB-INF/admin/edit-course.jsp").include(req, resp);

    }

}
