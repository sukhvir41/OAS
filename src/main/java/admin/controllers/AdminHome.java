/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import student.attendanceWsService.MacHandlers;
import utility.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
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
@WebServlet(urlPatterns = "/admin")
public class AdminHome extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession,
                        PrintWriter out) throws Exception {

        req.setAttribute(UserType.Student.toString(), UserType.Student.getCount());
        req.setAttribute(UserType.Admin.toString(), UserType.Admin.getCount());
        req.setAttribute(UserType.Teacher.toString(), UserType.Student.getCount());

        req.setAttribute("handlerReady", MacHandlers.isHandlerReady());
        req.setAttribute("details", MacHandlers.getDetails());

        UUID id = (UUID) httpSession.getAttribute("userId");

        Admin theAdmin = EntityHelper.getInstance(id, Admin_.id, Admin.class, session, true, Admin_.USER);

        extendCookie(req, resp, httpSession, theAdmin);

        httpSession.setAttribute(UserType.Admin.getType().toLowerCase(), theAdmin);

        req.getRequestDispatcher("WEB-INF/admin/home.jsp")
                .include(req, resp);

    }

    private void extendCookie(HttpServletRequest req, HttpServletResponse resp, HttpSession httpSession, Admin admin) {

        try {
            if ((boolean) httpSession.getAttribute("extendCookie")) {
                httpSession.setAttribute("extendCookie", false);
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
                if (ObjectUtils.allNotNull(id, token)) {
                    id.setMaxAge(864000);
                    token.setMaxAge(864000);
                    resp.addCookie(id);
                    resp.addCookie(token);

                }
                admin.getUser()
                        .setSessionExpiryDate(LocalDateTime.now());
            }


        } catch (Exception e) {
            System.out.println("could not extend login session cookies");
        }
    }
}
