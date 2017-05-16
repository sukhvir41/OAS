/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Controller;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/logout")
public class LogOut extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        Utils.userLoggedOut(httpSession);

        httpSession.removeAttribute("accept");
        httpSession.invalidate();

        String sessionId = "";

        for (Cookie c : req.getCookies()) {
            if (c.getName().equals("sid")) {
                sessionId = c.getValue();
                c.setMaxAge(0);
                resp.addCookie(c);
            } else if (c.getName().equals("stoken")) {
                c.setMaxAge(0);
                resp.addCookie(c);
            }
        }

        List<User> users = session.createCriteria(User.class)
                .add(Restrictions.eq("sessionId", sessionId))
                .list();

        if (users.size() > 0) {
            User user = users.get(0);
            user.setSessionId(null);
            user.setSessionToken("" + Utils.generateBase64());
            session.update(user);
        }

        resp.sendRedirect("login");

    }

}
