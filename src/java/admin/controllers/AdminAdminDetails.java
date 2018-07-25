/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Admin;
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
@WebServlet(urlPatterns = "/admin/admins/detailadmin")
public class AdminAdminDetails extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        System.out.println("callld  this methods to show");
        
        Long adminId = Long.parseLong(req.getParameter("adminId"));

        Admin admin = (Admin) session.get(Admin.class, adminId);

        req.setAttribute("admin", admin);
        req.getRequestDispatcher("/WEB-INF/admin/detailadmin.jsp").include(req, resp);

    }

}