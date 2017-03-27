/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sukhvir
 */
@WebServlet("/student")
public class StudentHome extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/student/studenthome.jsp").include(req, resp);
        extendCookie(req, resp);
    }

    private void extendCookie(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
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
            }

        } catch (Exception e) {
            System.out.println("error in extend cookie");
        }
    }
}
