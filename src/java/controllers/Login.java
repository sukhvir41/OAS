/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Admin;
import entities.DefaultVisitor;
import entities.Student;
import entities.Teacher;
import entities.User;
import entities.UserType;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
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
@WebServlet({"/login",})
public class Login extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        System.out.println("login called");
        if (httpSession.getAttribute("accept") != null) {
            if ((boolean) httpSession.getAttribute("accept")) {
                switch ((UserType) httpSession.getAttribute("type")) {
                    case Student:
                        resp.sendRedirect("/OAS/student");
                        break;
                    case Teacher:
                        resp.sendRedirect("/OAS/teacher");
                        break;
                    case Admin:
                        resp.sendRedirect("/OAS/admin");
                        break;
                }
            }
        } else {
            checkCookies(req, resp, session, httpSession);
        }

    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        resp.setDateHeader("Expires", 0);
        req.getRequestDispatcher("/WEB-INF/login.jsp").include(req, resp);
    }

    private void checkCookies(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession) throws Exception {
        Cookie id = null, token = null;
        for (Cookie cookie : req.getCookies()) {
            switch (cookie.getName()) {
                case "sid":
                    id = cookie;
                    break;
                case "stoken":
                    token = cookie;
                    break;
            }
        }

        User user = (User) session.createCriteria(User.class)
                .add(Restrictions.eq("sessionId", id.getValue()))
                .list()
                .get(0);

        if (user.matchSessionToken(token.getValue())) {
            forward(req, resp, user, session, httpSession);
        } else {
            onError(req, resp);
        }

    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, User user, Session session, HttpSession httpSession) throws Exception {

        User loggedUser = user.accept(DefaultVisitor.getInstance());

        System.out.println(loggedUser.getClass().getName());   // print statement

        if (loggedUser instanceof Student) {

            httpSession.setAttribute("accept", true);
            httpSession.setAttribute("extenedCookie", true);
            httpSession.setAttribute("type", UserType.Student);
            httpSession.setAttribute("student", (Student) loggedUser);

            Utils.userLoggedIn(httpSession);

            resp.sendRedirect("/OAS/student");

            return;
        } else if (loggedUser instanceof Teacher) {
            httpSession.setAttribute("accept", true);
            httpSession.setAttribute("extenedCookie", true);
            httpSession.setAttribute("type", UserType.Teacher);
            httpSession.setAttribute("teacher", (Teacher) loggedUser);

            Utils.userLoggedIn(httpSession);

            resp.sendRedirect("/OAS/teacher");

            return;
        } else if (loggedUser instanceof Admin) {
            httpSession.setAttribute("accept", true);
            httpSession.setAttribute("extenedCookie", true);
            httpSession.setAttribute("type", UserType.Admin);
            httpSession.setAttribute("admin", (Admin) loggedUser);

            Utils.userLoggedIn(httpSession);

            resp.sendRedirect("/OAS/admin");

            return;
        } else {
            onError(req, resp);
        }

    }
}
