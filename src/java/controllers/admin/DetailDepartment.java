/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Course;
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
@WebServlet(urlPatterns = "/admin/departments/detaildepartment")
public class DetailDepartment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException , IOException{
        int departmentID = 1;
        try {
            departmentID = Integer.parseInt(req.getParameter("departmentId"));
        } catch (Exception e) {
        }
        Department department;
        List<Teacher> teachers;
        List<Course> courses;
        Teacher hod;
        Session session = Utils.openSession();
        session.beginTransaction();
        department = (Department) session.get(Department.class, departmentID);
        teachers = department.getTeachers();
        courses = department.getCourses();
        hod = department.getHod();
        req.setAttribute("department", department);
        req.setAttribute("hod", hod);
        req.setAttribute("teachers", teachers);
        req.setAttribute("courses", courses);
        req.getRequestDispatcher("/WEB-INF/admin/detaildepartment.jsp").include(req, resp);
        session.getTransaction().commit();
        session.close();
    }

}
