/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Student;
import entities.Teacher;
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
                switch ((String) httpSession.getAttribute("type")) {
                    case "student":
                        resp.sendRedirect("/OAS/student");
                        break;
                    case "teacher":
                        resp.sendRedirect("/OAS/teacher");
                        break;
                    case "admin":
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
        entities.Login login = (entities.Login) session.createCriteria(entities.Login.class)
                .add(Restrictions.eq("sessionId", id.getValue()))
                .list()
                .get(0);

        if (login.matchSessionToken(token.getValue())) {
            forward(req, resp, login, session, httpSession);
        }

    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, entities.Login login, Session session, HttpSession httpSession) throws Exception {
        switch (login.getType()) {
            case "admin": {
                httpSession.setAttribute("accept", true);
                httpSession.setAttribute("extenedCookie", true);
                httpSession.setAttribute("type", "admin");
                httpSession.setAttribute("admin", login);
                resp.sendRedirect("/OAS/admin");
                break;
            }
            case "student": {
                httpSession.setAttribute("accept", true);
                httpSession.setAttribute("extenedCookie", true);
                httpSession.setAttribute("type", "student");
                Student student = (Student) session.get(Student.class, login.getId());
                httpSession.setAttribute("student", student);
                resp.sendRedirect("/OAS/student");
                break;
            }
            case "teacher": {
                httpSession.setAttribute("accept", true);
                httpSession.setAttribute("extenedCookie", true);
                httpSession.setAttribute("type", "teacher");
                Teacher teacher = (Teacher) session.get(Teacher.class, login.getId());
                httpSession.setAttribute("teacher", teacher);
                resp.sendRedirect("/OAS/teacher");
                break;
            }

        }
    }
}
