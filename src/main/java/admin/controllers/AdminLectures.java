/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Course;
import entities.Course_;
import entities.EntityHelper;
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
@WebServlet(urlPatterns = "/admin/lectures")
public class AdminLectures extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        List<Course> courses = EntityHelper.getAll(session, Course.class, Course_.name, true);

        req.setAttribute("courses", courses);
        req.getRequestDispatcher("/WEB-INF/admin/admin-admin-lectures.jsp")
                .include(req, resp);
    }

}
