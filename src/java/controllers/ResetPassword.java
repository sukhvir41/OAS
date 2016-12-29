/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Login;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
        String token = req.getParameter("token");
        Login login;
        if (username != null && !username.equals("") && token != null && !token.equals("")) {
            Date now = new Date();
            Session session = Utils.openSession();
            session.beginTransaction();
            login = (Login) session.get(Login.class, username);
            session.getTransaction().commit();
            session.close();
            if (login == null) {
                resp.sendRedirect("error");
            } else {
                if (!login.isUsed()) {
                    if (token.equals(login.getToken())) {
                        if ((Math.abs(now.getTime() - login.getDate().getTime()) / 60000d) <= 30d) {
                            req.setAttribute("username", login.getUsername());
                            req.getRequestDispatcher("WEB-INF/resetpassword.jsp").forward(req, resp);
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
