/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Course;
import entities.Department;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/courses")
public class AdminCourse extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        List<Course> courses = (List<Course>) session.createCriteria(Course.class)
                .list();
        
        List<Department> departments = (List<Department>) session.createCriteria(Department.class)
                .list();

        req.setAttribute("courses", courses);
        req.setAttribute("departments", departments);

        req.getRequestDispatcher("/WEB-INF/admin/admin-course.jsp")
                .include(req, resp);
    }

}
