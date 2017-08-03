/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.Admin;
import entities.AdminType;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/admins/deleteadmin")
public class DeleteAdmin extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        String username = req.getParameter("username");

        Admin admin = (Admin) session.createCriteria(Admin.class)
                .add(Restrictions.eq("username", username))
                .list()
                .get(0);

        if (admin.getType().equals(AdminType.Sub)) {
            session.delete(admin);
        }

        resp.sendRedirect("/OAS/admin/admins");

    }

}
