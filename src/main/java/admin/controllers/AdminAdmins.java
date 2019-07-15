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
import org.hibernate.graph.RootGraph;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/admins")
public class AdminAdmins extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        RootGraph<Admin> rootGraph = session.createEntityGraph(Admin.class);
        rootGraph.addAttributeNodes(Admin_.USER);

        List<Admin> admins = EntityHelper.getAll(session, Admin.class, rootGraph, true);

        req.setAttribute("admins", admins);
        req.getRequestDispatcher("/WEB-INF/admin/admin-admins.jsp")
                .include(req, resp);

    }

}
