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
import utility.Controller;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/admins/deleteadminpost")
public class DeleteAdmin extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

       
        Long adminId = Long.parseLong(req.getParameter("adminId"));

        Admin admin = (Admin) session.get(Admin.class, adminId);

        if (admin.getType().equals(AdminType.Sub)) {
            session.delete(admin);
        }

        resp.sendRedirect("/OAS/admin/admins");

    }

    @Override
    public boolean showErrorLog() {
        return true;
    }

}
