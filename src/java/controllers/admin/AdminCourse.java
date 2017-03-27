/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Course;
import entities.Department;
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
@WebServlet(urlPatterns = "/admin/courses")
public class AdminCourse extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Course> courses;
        List<Department> departments;
        Session session = Utils.openSession();
        session.beginTransaction();
        courses = (List<Course>) session.createQuery("from Course").list();
        departments = (List<Department>) session.createQuery("from Department").list();
        session.getTransaction().commit();
        session.close();
        req.setAttribute("courses", courses);
        req.setAttribute("departments", departments);
        req.getRequestDispatcher("/WEB-INF/admin/admincourse.jsp").include(req, resp);
    }

}
