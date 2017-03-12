/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Login;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/logout")
public class LogOut extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Session session = Utils.openSession();
        session.beginTransaction();
        try {

            out.print("loging out");
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("accept", null);
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
            List<Login> logins = session.createCriteria(Login.class)
                    .add(Restrictions.eq("sessionId", sessionId))
                    .list();
            if (logins.size() > 0) {
                Login login = logins.get(0);
                login.setSessionId(null);
                login.setSessionToken(""+Utils.generateBase64());
                session.update(login);
            }
            session.getTransaction().commit();
            session.close();
            resp.sendRedirect("login");
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
            resp.sendRedirect("/OAS/error");

        } finally {
            out.close();
        }

    }

}
