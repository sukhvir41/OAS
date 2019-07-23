/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Department;
import entities.Department_;
import entities.EntityHelper;
import entities.Teacher;
import org.hibernate.Session;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/teachers")
public class AdminTeachers extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        List<Department> departments = EntityHelper.getAll(session, Department.class, Department_.name, true);

        req.setAttribute("departments", departments);

        req.getRequestDispatcher("/WEB-INF/admin/admin-teacher.jsp")
                .include(req, resp);
    }

}
