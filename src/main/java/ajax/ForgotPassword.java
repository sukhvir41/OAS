/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import entities.User;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;
import utility.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * @author sukhvir
 * <p>
 * todo: have to re qrite this entire thing
 */
@WebServlet(urlPatterns = "/ajax/forgot-password")
public class ForgotPassword extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        String email = req.getParameter("email");
        if (Utils.regexMatch("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", email, Pattern.CASE_INSENSITIVE)) {
            if (email != null && !email.equals("")) {

                String body = "This is an auto generated mail. Do not reply or send any mail to this address."
                        + "Click on the link to reset your password in 30 mins or it will get expired. ";

                User user = (User) session.createCriteria(User.class)
                        .add(Restrictions.eq("email", email))
                        .list()
                        .get(0);

                String token = Utils.createToken();
                user.setToken(token);
                //user.setDate(LocalDateTime.now());
                session.update(user);
                String url = "192.168.1.1/OAS/reset-password?username=" + user.getUsername() + "&token=" + URLEncoder.encode(token, "UTF-8");
                Utils.sendMail(email, "Password reset.  OAS system", body + url);
                out.print(true);

            } else {
                out.print(false);
            }
        } else {
            out.print(false);
        }
    }

}
