/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Teacher;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/teachers/detailteacher")
public class DetailTeacher extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int teacherId = Integer.parseInt(req.getParameter("teacherId"));

        Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);

        req.setAttribute("teacher", teacher);
        req.setAttribute("username", teacher.getUser().getUsername());
        req.setAttribute("username", teacher.getUser().getUsername());

        req.getRequestDispatcher("/WEB-INF/admin/detailteacher.jsp").include(req, resp);

    }

}