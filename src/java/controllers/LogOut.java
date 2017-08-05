/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import entities.UserType;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
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

//        String sessionId = "";
        for (Cookie c : req.getCookies()) {
            if (c.getName().equals("sid")) {
//                sessionId = c.getValue();
                c.setMaxAge(0);
                resp.addCookie(c);
            } else if (c.getName().equals("stoken")) {
                c.setMaxAge(0);
                resp.addCookie(c);
            }
        }
        
        User loggedUser = (User) httpSession.getAttribute(((UserType) httpSession.getAttribute("type")).toString());
        loggedUser.getUserType()
                .decrementCount();
        
        User user = (User) session.get(User.class, loggedUser.getId());
        
        user.setSessionId(null);
        user.setSessionToken("" + Utils.generateBase64());
        
        httpSession.removeAttribute("accept");
        httpSession.invalidate();

//        List<User> users = session.createCriteria(User.class)
//                .add(Restrictions.eq("sessionId", sessionId))
//                .list();
//
//        if (users.size() > 0) {
//            User user = users.get(0);
//            user.setSessionId(null);
//            user.setSessionToken("" + Utils.generateBase64());
//            session.update(user);
//        }
        resp.sendRedirect("/OAS/login");
        
    }
    
}
