/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Course;
import entities.Subject;
import java.io.IOException;
import java.util.List;
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
@WebServlet(urlPatterns = "/admin/subjects")
public class AdminSubject extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Subject> subjects;
        List<Course> courses;
        Session session = Utils.openSession();
        session.beginTransaction();
        subjects = (List<Subject>) session.createCriteria(Subject.class).list();
        courses = (List<Course>) session.createCriteria(Course.class).list();
        session.getTransaction().commit();
        session.close();
        req.setAttribute("subjects", subjects);
        req.setAttribute("courses", courses);
        req.getRequestDispatcher("/WEB-INF/admin/adminsubject.jsp").forward(req, resp);
    }

}
