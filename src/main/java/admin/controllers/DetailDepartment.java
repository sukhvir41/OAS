/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Course;
import entities.Department;
import entities.Teacher;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/departments/detaildepartment")
public class DetailDepartment extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        long departmentID = Long.parseLong(req.getParameter("departmentId"));

        Department department = (Department) session.get(Department.class, departmentID);
        Set<Teacher> teachers = department.getTeachers();
        List<Course> courses = department.getCourses();
        Teacher hod = department.getHod();

        req.setAttribute("department", department);
        req.setAttribute("hod", hod);
        req.setAttribute("teachers", teachers);
        req.setAttribute("courses", courses);
        req.getRequestDispatcher("/WEB-INF/admin/detaildepartment.jsp").include(req, resp);

    }

}
