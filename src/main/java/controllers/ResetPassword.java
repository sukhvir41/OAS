/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import javax.servlet.annotation.WebServlet;
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
@WebServlet(urlPatterns = "/resetpassword")
public class ResetPassword extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        String username = req.getParameter("username");
        String token = URLDecoder.decode(req.getParameter("token"), "UTF-8");

        if (username != null && !username.equals("") && token != null && !token.equals("")) {
            LocalDateTime presentTime = LocalDateTime.now();

            User user = (User) session.createCriteria(User.class)
                    .add(Restrictions.eq("username", username))
                    .list()
                    .get(0);

            System.out.println(token);

            if (!user.isUsed()) {

                if (token.equals(user.getToken())) {

                    LocalDateTime endTime = LocalDateTime.of(user.getDate().toLocalDate(), user.getDate().toLocalTime()).plusMinutes(30);

                    if (presentTime.isAfter(user.getDate()) && presentTime.isBefore(endTime)) {

                        req.setAttribute("username", user.getUsername());
                        req.getRequestDispatcher("WEB-INF/resetpassword.jsp").include(req, resp);

                    } else {

                        resp.sendRedirect("expired");
                    }

                } else {
                    resp.sendRedirect("error");
                }
            } else {
                resp.sendRedirect("error");
            }

        } else {
            resp.sendRedirect("error");
        }

    }

}
