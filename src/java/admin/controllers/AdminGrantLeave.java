/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Student;
import java.io.PrintWriter;
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
@WebServlet(urlPatterns = "/admin/students/grantleave")
public class AdminGrantLeave extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        long studentId = Long.parseLong(req.getParameter("studentId"));
        Student student = (Student) session.get(Student.class, studentId);
        req.setAttribute("student", student);
        req.getRequestDispatcher("/WEB-INF/admin/admingrantleave.jsp")
                .include(req, resp);
    }

}
