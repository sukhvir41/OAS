/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Course;
import entities.Course_;
import entities.Department;
import entities.Department_;
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
@WebServlet(urlPatterns = "/admin/courses")
public class AdminCourse extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        //getting the departments of the course graph
        var courseRootGraph = session.createEntityGraph(Course.class);
        courseRootGraph.addAttributeNode(Course_.DEPARTMENT);

        var courseBuilder = session.getCriteriaBuilder();
        var courseQuery = courseBuilder.createQuery(Course.class);
        var courseRoot = courseQuery.from(Course.class);

        //ordering by course name
        courseQuery.orderBy(courseBuilder.asc(courseRoot.get(Course_.name)));


        List<Course> courses = session.createQuery(courseQuery)
                .applyLoadGraph(courseRootGraph)
                .setReadOnly(true)
                .getResultList();

        var departmentBuilder = session.getCriteriaBuilder();
        var departmentQuery = departmentBuilder.createQuery(Department.class);
        var departmentRoot = departmentQuery.from(Department.class);

        //ordering by department name
        departmentQuery.orderBy(departmentBuilder.asc(departmentRoot.get(Department_.name)));

        List<Department> departments = session.createQuery(departmentQuery)
                .setReadOnly(true)
                .getResultList();

        req.setAttribute("courses", courses);
        req.setAttribute("departments", departments);

        req.getRequestDispatcher("/WEB-INF/admin/admin-course.jsp")
                .include(req, resp);
    }

}
