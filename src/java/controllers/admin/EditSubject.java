/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

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
@WebServlet(urlPatterns = "/admin/subjects/editsubject")
public class EditSubject extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int subjectId = Integer.parseInt(req.getParameter("subjectId"));
        Session session = Utils.openSession();
        session.beginTransaction();  
        Subject subject = (Subject) session.get( Subject.class, subjectId);
        req.setAttribute("subject", subject);
        req.getRequestDispatcher("/WEB-INF/admin/editsubject.jsp").include(req, resp);
        session.getTransaction().commit();
        session.close();
        
    }
    
}
