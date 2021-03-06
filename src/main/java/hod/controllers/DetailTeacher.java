/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hod.controllers;

import entities.Department;
import entities.Teacher;
import org.hibernate.Session;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/hod/teachers/detailteacher")
public class DetailTeacher extends Controller {

    @Override
    public void process(
            HttpServletRequest req,
            HttpServletResponse resp,
            Session session,
            HttpSession httpSession,
            PrintWriter out) throws Exception {
        int teacherId = Integer.parseInt(req.getParameter("teacherId"));
        Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);

        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());

        if (teacher.getDepartments().contains(department)) {

            req.setAttribute("username", teacher.getUser().getUsername());
            req.setAttribute("teacher", teacher);
            req.getRequestDispatcher("/WEB-INF/hod/detailteacher.jsp").include(req, resp);
        } else {
            resp.sendRedirect("/OAS/error");
        }
    }

}
