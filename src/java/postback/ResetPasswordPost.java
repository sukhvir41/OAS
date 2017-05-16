/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback;

import entities.User;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.PostBackController;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
//todo: this is not secure have to fix it
@WebServlet(urlPatterns = "/resetpasswordpost")
public class ResetPasswordPost extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = (User) session.createCriteria(User.class)
                .add(Restrictions.eq("username", username))
                .list()
                .get(0);

        user.setPassword(password);
        user.setUsed(true);

        session.update(user);

        String body = "this is an autogenerated mail. Please don't reply or send mail to this address. Your password was changed on " + new Date();
        Utils.sendMail(user.getEmail(), "Password changed", body);

        resp.sendRedirect("login");
    }

}
