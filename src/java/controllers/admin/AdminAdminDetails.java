/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Login;
import entities.UserType;
import java.io.IOException;
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
@WebServlet(urlPatterns = "/admin/admins/admindetails")
public class AdminAdminDetails extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            String username = req.getParameter("username");

            Login admin = (Login) session.get(Login.class, username);
            session.getTransaction().commit();
            session.close();

            req.setAttribute("admin", admin);
            req.getRequestDispatcher("/WEB-INF/admin/adminadmindetails.jsp").forward(req, resp);

        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            resp.sendRedirect("/OAS/error");
        }
    }

}
