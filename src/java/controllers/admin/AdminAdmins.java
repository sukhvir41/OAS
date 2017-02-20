/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Login;
import entities.UserType;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/admins")
public class AdminAdmins extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Login> admins;
        Session session = Utils.openSession();
        session.beginTransaction();
        admins = session.createCriteria(Login.class).add(Restrictions.eq("type", UserType.Admin.toString())).list();
        req.setAttribute("admins", admins);
        req.getRequestDispatcher("/WEN-INF/adminadmins.jsp").forward(req, resp);
        session.getTransaction().commit();
        session.close();

    }

}
