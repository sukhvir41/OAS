/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Teacher;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/teachers/unaccounted")
public class AdminUnaccountedTeachers extends Controller {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        List<Teacher> teachers = session.createCriteria(Teacher.class)
                .add(Restrictions.eq("unaccounted", true))
                .list();
        
        req.setAttribute("teachers", teachers);
        req.getRequestDispatcher("/WEB-INF/admin/unaccountedteacher.jsp").include(req, resp);
    }
    
}
