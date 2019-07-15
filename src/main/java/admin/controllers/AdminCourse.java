/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.*;
import org.hibernate.Session;
import org.hibernate.tuple.entity.EntityTuplizer;
import utility.Controller;
import utility.Utils;

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
        courseRootGraph.addAttributeNodes(Course_.DEPARTMENT);

        List<Course> courses = EntityHelper.getAll(session, Course.class, courseRootGraph, true);

        List<Department> departments = EntityHelper.getAll(session, Department.class, Department_.name, true);

        req.setAttribute("courses", courses);
        req.setAttribute("departments", departments);

        req.getRequestDispatcher("/WEB-INF/admin/admin-course.jsp")
                .include(req, resp);
    }

}
