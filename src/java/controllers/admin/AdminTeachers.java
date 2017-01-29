/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Department;
import entities.Teacher;
import java.io.IOException;
import java.util.List;
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
@WebServlet(urlPatterns = "/admin/teachers")
public class AdminTeachers extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        List<Department> departments = (List<Department>) session.createCriteria(Department.class).list();
        List<Teacher> teachers = (List<Teacher>) session.createCriteria(Teacher.class).list();

        req.setAttribute("teachers", teachers);
        req.setAttribute("departments", departments);
        req.getRequestDispatcher("/WEB-INF/admin/adminteacher.jsp").forward(req, resp);
        session.getTransaction().commit();
        session.close();
    }

}
