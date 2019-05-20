/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Teacher;
import org.hibernate.Session;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/teachers/activateteacher")
public class ActivateTeacher extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        long teacherId = Long.parseLong(req.getParameter("teacherId"));
        Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);

        if (teacher.isUnaccounted()) {
            resp.sendRedirect("/OAS/admin/teachers/detailteacher?teacherId=" + teacher.getId() + "&error=true");
        } else {
            teacher.setVerified(true);
            resp.sendRedirect("/OAS/admin/teachers/detailteacher?teacherId=" + teacher.getId());
        }

    }

}
