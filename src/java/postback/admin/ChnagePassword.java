/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.Admin;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/myaccount/changepasswordpost")
public class ChnagePassword extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        String oldPassword = req.getParameter("oldpassword");
        String newPassword = req.getParameter("newpassword");
        String renewPassword = req.getParameter("renewpassword");

        Admin admin = (Admin) req.getSession().getAttribute("admin");
        if (admin.checkPassword(oldPassword)) {
            if (newPassword.length() >= 8 && newPassword.length() <= 40 && newPassword.equals(renewPassword)) {
                admin.setPassword(newPassword);
                session.update(admin);

                req.getSession().setAttribute("admin", admin);
                resp.sendRedirect("/OAS/admin/myaccount/changepassword?error=false");
            } else {

                resp.sendRedirect("/OAS/admin/myaccount/changepassword?error=true");
            }
        } else {

            resp.sendRedirect("/OAS/admin/myaccount/changepassword?error=true");
        }

    }

}
