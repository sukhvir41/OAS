/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Login;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/resetpassword")
public class ResetPassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String token = URLDecoder.decode(req.getParameter("token"), "UTF-8");
        Login login;
        if (username != null && !username.equals("") && token != null && !token.equals("")) {
            LocalDateTime presentTime = LocalDateTime.now();
            Session session = Utils.openSession();
            session.beginTransaction();
            login = (Login) session.get(Login.class, username);
            System.out.println(token);
            if (login == null) {
                resp.sendRedirect("error");
            } else {
                if (!login.isUsed()) {
                    if (token.equals(login.getToken())) {
                        LocalDateTime endTime = LocalDateTime.of(login.getDate().toLocalDate(), login.getDate().toLocalTime()).plusMinutes(30);
                        if (presentTime.isAfter(login.getDate()) && presentTime.isBefore(endTime)) {
                            req.setAttribute("username", login.getUsername());
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
            }
            session.getTransaction().commit();
            session.close();
        } else {
            resp.sendRedirect("error");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("error");
        out.close();
    }

}
