/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Student;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.Controller;
import utility.UrlBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/students/grant-leave")
public class AdminGrantLeave extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var studentIdString = req.getParameter("studentId");


        if (StringUtils.isBlank(studentIdString)) {
            var params = new UrlBuilder();
            params.addErrorParameter()
                    .addMessage("The student you are trying to access does not exist");

            resp.sendRedirect(params.getUrl("/OAS/admin/students"));
            return;
        }

        UUID studentId = UUID.fromString(studentIdString);

        Student student = session.get(Student.class, studentId);

        req.setAttribute("student", student);
        req.getRequestDispatcher("/WEB-INF/admin/grant-leave.jsp")
                .include(req, resp);
    }

}
