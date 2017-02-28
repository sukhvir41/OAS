/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.Login;
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
@WebServlet(urlPatterns = "/admin/myaccount/changepasswordpost")
public class ChnagePassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            String oldPassword = req.getParameter("oldpassword");
            String newPassword = req.getParameter("newpassword");
            String renewPassword = req.getParameter("renewpassword");

            Login admin = (Login) req.getSession().getAttribute("admin");
            if (admin.checkPassword(oldPassword)) {
                if (newPassword.length() >= 8 && newPassword.length() <= 40 && newPassword.equals(renewPassword)) {
                    admin.setPassword(newPassword);
                    session.update(admin);
                    session.getTransaction().commit();
                    session.close();
                    req.getSession().setAttribute("admin", admin);
                    resp.sendRedirect("/OAS/admin/myaccount/changepassword?error=false");
                } else {
                    session.getTransaction().rollback();
                    session.close();
                    resp.sendRedirect("/OAS/admin/myaccount/changepassword?error=true");
                }
            } else {
                session.getTransaction().rollback();
                session.close();
                resp.sendRedirect("/OAS/admin/myaccount/changepassword?error=true");
            }

        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();

            resp.sendRedirect("/OAS/error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/admin");
    }

}
