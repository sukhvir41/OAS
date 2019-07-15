/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Course;
import entities.EntityHelper;
import entities.Subject;
import entities.Subject_;
import org.hibernate.Session;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/subjects")
public class AdminSubject extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var subjectGraph = session.createEntityGraph(Subject.class);
        subjectGraph.addAttributeNodes(Subject_.CLASS_ROOMS, Subject_.COURSE);

        List<Subject> subjects = EntityHelper.getAll(session, Subject.class, subjectGraph, true);

        List<Course> courses = EntityHelper.getAll(session, Course.class, true);

        req.setAttribute("subjects", subjects);
        req.setAttribute("courses", courses);

        req.getRequestDispatcher("/WEB-INF/admin/admin-subject.jsp")
                .include(req, resp);
    }

}
