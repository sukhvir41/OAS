/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Admin;
import entities.Admin_;
import entities.EntityHelper;
import entities.UserType;
import org.hibernate.Session;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/my-account")
public class AdminAccountDetails extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Admin admin = (Admin) httpSession.getAttribute(UserType.Admin.toString());

        admin = EntityHelper.getInstance(admin.getId(), Admin_.id, Admin.class, session, true, Admin_.USER);

        req.setAttribute("admin", admin);
        req.getRequestDispatcher("/WEB-INF/admin/my-account.jsp")
                .include(req, resp);
    }

}
