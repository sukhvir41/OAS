/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.Subject;
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
@WebServlet(urlPatterns = "/admin/subjects/deletesubject")
public class DeleteSubject extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int subjectId = Integer.parseInt(req.getParameter("subjectId"));
        Session session = Utils.openSession();
        session.beginTransaction();
        Subject subject = (Subject) session.get(Subject.class, subjectId);
        subject.getClassRooms().stream()
                .forEach(e -> e.getSubjects().remove(subject));
        session.delete(subject);
        session.getTransaction().commit();
        session.close();
        resp.sendRedirect("/OAS/admin/subjects");
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/admin/subejcts");
    }
    
}
