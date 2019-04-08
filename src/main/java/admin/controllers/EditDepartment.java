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

import entities.Department;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/departments/editdepartment")
public class EditDepartment extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        long departmentId = Long.parseLong(req.getParameter("departmentId"));

        Department department = (Department) session.get(Department.class, departmentId);

        req.setAttribute("department", department);
        req.getRequestDispatcher("/WEB-INF/admin/editdepartment.jsp").include(req, resp);

    }

}
