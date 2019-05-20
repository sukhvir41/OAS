/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Subject;
import org.hibernate.Session;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/subjects/editsubject")
public class EditSubject extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int subjectId = Integer.parseInt(req.getParameter("subjectId"));

        Subject subject = (Subject) session.get(Subject.class, subjectId);
        req.setAttribute("subject", subject);

        req.getRequestDispatcher("/WEB-INF/admin/editsubject.jsp").include(req, resp);
    }

}
