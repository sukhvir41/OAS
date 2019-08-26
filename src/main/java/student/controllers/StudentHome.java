/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.controllers;

import entities.EntityHelper;
import entities.Student;
import entities.Student_;
import entities.UserType;
import org.hibernate.Session;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author sukhvir
 */
@WebServlet("/student")
public class StudentHome extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        UUID id = (UUID) httpSession.getAttribute("userId");
        Student student = EntityHelper.getInstance(id, Student_.id, Student.class, session, true, Student_.USER);

        extendCookie(req, resp, httpSession, student);
        httpSession.setAttribute(UserType.Student.getType().toLowerCase(), student);

        req.getRequestDispatcher("/WEB-INF/student/studenthome.jsp").include(req, resp);
    }

    private void extendCookie(HttpServletRequest req, HttpServletResponse resp, HttpSession session, Student student) {
        try {
            if ((boolean) session.getAttribute("extenedCookie")) {
                session.setAttribute("extenedCookie", false);
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

                id.setMaxAge(864000);
                token.setMaxAge(864000);
                resp.addCookie(id);
                resp.addCookie(token);

                student.getUser().setSessionExpiryDate(LocalDateTime.now());
            }


        } catch (Exception e) {
            System.out.println("error in extend cookie");
        }
    }
}
