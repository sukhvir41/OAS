/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hod.controllers;

import entities.Department;
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
@WebServlet(urlPatterns = "/teacher/hod/subjects/detailsubject")
public class DetailSubject extends Controller {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int subjectId = Integer.parseInt(req.getParameter("subjectId"));
        Subject subject = (Subject) session.get(Subject.class, subjectId);
        
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());
        
        if (subject.getCourse().getDepartment().getId() == department.getId()) {
            req.setAttribute("subject", subject);
            req.getRequestDispatcher("/WEB-INF/hod/detailsubject.jsp").include(req, resp);
        } else {
            resp.sendRedirect("/OAS/error");
        }
    }
    
}
