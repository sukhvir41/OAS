/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.hod;

import entities.Subject;
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
@WebServlet(urlPatterns = "/teacher/hod/subejcts/detailsubject")
public class DetailSubject extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int subjectId = Integer.parseInt(req.getParameter("subjectId"));
        Subject subject = (Subject) session.get(Subject.class, subjectId);

        req.setAttribute("subject", subject);
        req.getRequestDispatcher("/WEB-INF/hod/detailsubject.jsp").include(req, resp);

    }

}
