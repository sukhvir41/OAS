/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Admin;
import entities.Admin_;
import entities.EntityHelper;
import org.hibernate.Session;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/admins/admin-details")
public class AdminDetails extends Controller {

    @Override
    public void process(
            HttpServletRequest req,
            HttpServletResponse resp,
            Session session,
            HttpSession httpSession,
            PrintWriter out) throws Exception {

        UUID adminId = UUID.fromString(req.getParameter("adminId"));

        Admin admin = EntityHelper.getInstance(adminId, Admin_.id, Admin.class, session, true, Admin_.USER);

        req.setAttribute("admin", admin);
        req.getRequestDispatcher("/WEB-INF/admin/admin-details.jsp")
                .include(req, resp);

    }

}
