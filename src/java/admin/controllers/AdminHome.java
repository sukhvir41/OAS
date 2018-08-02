/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import AttendanceServices.MacHandlers;
import entities.UserType;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin")
public class AdminHome extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        req.setAttribute(UserType.Student.toString(), UserType.Student.getCount());
        req.setAttribute(UserType.Admin.toString(), UserType.Admin.getCount());
        req.setAttribute(UserType.Teacher.toString(), UserType.Student.getCount());

        req.setAttribute("handlerReady", MacHandlers.isHandlerReady());
        req.setAttribute("details", MacHandlers.getDetails());

        req.getRequestDispatcher("WEB-INF/admin/adminhome.jsp").include(req, resp);
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
                if(id != null && token != null)
                id.setMaxAge(864000);
                token.setMaxAge(864000);
                resp.addCookie(id);
                resp.addCookie(token);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in extend cookie");
        }
    }
}
