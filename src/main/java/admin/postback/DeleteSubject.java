/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.Subject;
import org.hibernate.Session;
import utility.PostBackController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/subjects/deletesubject")
public class DeleteSubject extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        int subjectId = Integer.parseInt(req.getParameter("subjectId"));

        Subject subject = (Subject) session.get(Subject.class, subjectId);
        /*subject.getClassRooms()
                .stream()
                .forEach(e -> e.getSubjects().remove(subject));*/
        session.delete(subject);

        resp.sendRedirect("/OAS/admin/subjects");

    }

}
