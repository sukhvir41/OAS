/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Course;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/courses/detailcourse")
public class DetailCourse extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int courseId = 1;
        try {
            courseId = Integer.parseInt(req.getParameter("courseId"));
        } catch (Exception e) {
        }
        Course course;
        Session session = Utils.openSession();
        session.beginTransaction();
        course = (Course) session.get(Course.class, courseId);
        req.setAttribute("course", course);
        req.getRequestDispatcher("/WEB-INF/admin/detailcourse.jsp").forward(req, resp);
        session.getTransaction().commit();
        session.close();

    }

}
