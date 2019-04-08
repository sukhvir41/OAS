/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Admin;
import entities.UserType;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/myaccount")
public class AdminAccountDetails extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Admin admin = (Admin) httpSession.getAttribute(UserType.Admin.toString());
        admin = (Admin) session.get(Admin.class, admin.getId());
        req.setAttribute("admin", admin);
        req.getRequestDispatcher("/WEB-INF/admin/adminmyaccount.jsp")
                .include(req, resp);
    }

}
