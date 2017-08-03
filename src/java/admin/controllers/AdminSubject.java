/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Course;
import entities.Subject;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(urlPatterns = "/admin/subjects")
public class AdminSubject extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
     
        List<Subject> subjects = (List<Subject>) session.createCriteria(Subject.class).list();
        List<Course> courses = (List<Course>) session.createCriteria(Course.class).list();

        req.setAttribute("subjects", subjects);
        req.setAttribute("courses", courses);

        req.getRequestDispatcher("/WEB-INF/admin/adminsubject.jsp").include(req, resp);

    }

}
