/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback;

import entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
@WebServlet(urlPatterns = "/loginpost")
public class LoginPost extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("rememberme");

        User user;
        if (Utils.regexMatch("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", username, Pattern.CASE_INSENSITIVE)) {
            System.out.println("email worked"); /// print statement

            user = (User) session.createCriteria(User.class)
                    .add(Restrictions.eq("email", username))
                    .list()
                    .get(0);

        } else {
            user = (User) session.createCriteria(User.class)
                    .add(Restrictions.eq("username", username))
                    .list()
                    .get(0);
        }

        if (user.checkPassword(password)) {

            httpSession.setAttribute(user.getUserType().toString(), user);
            httpSession.setAttribute("type", user.getUserType());
            user.getUserType().incrementCount();

            httpSession.setAttribute("accept", true);

            if (remember != null && remember.equals("true")) {
                System.out.println("cookie send");
                httpSession.setMaxInactiveInterval(864000);

                String id = Utils.generateSessionId(session);
                Cookie cookieSessionId = new Cookie("sid", id);
                cookieSessionId.setMaxAge(864000);
                user.setSessionId(id);

                String token = Utils.createToken(12);
                Cookie cookieSessionToken = new Cookie("stoken", token);
                cookieSessionToken.setMaxAge(864000);
                user.setSessionToken(token);

                resp.addCookie(cookieSessionId);
                resp.addCookie(cookieSessionToken);
            }
            resp.sendRedirect("/OAS/" + user.getUserType().toString());

        } else {
            onError(req, resp);
        }
    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login?verified=false");
    }

}
