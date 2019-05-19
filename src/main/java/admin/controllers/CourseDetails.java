/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Course;
import entities.Course_;
import entities.EntityHelper;
import entities.Subject_;
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
@WebServlet(urlPatterns = "/admin/courses/course-details")
public class CourseDetails extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        long courseId = Long.parseLong(req.getParameter("courseId"));

        // both the course below are the same course but as jsp does not allow getting multiple buckets  in same query we have to write another query.

        var courseRootGraph = session.createEntityGraph(Course.class);
        courseRootGraph.addAttributeNodes(Course_.DEPARTMENT, Course_.CLASS_ROOMS);


        // this course gets the department and the list of classrooms
        Course course = EntityHelper.getInstance(courseId, Course_.id, Course.class, session, true, courseRootGraph);

        courseRootGraph = session.createEntityGraph(Course.class);
        var subjectsSubGraph = courseRootGraph.addSubgraph(Course_.SUBJECTS);
        subjectsSubGraph.addAttributeNodes(Subject_.CLASS_ROOMS);


        //this course gets subjects and it corresponding classrooms.
        Course course1 = EntityHelper.getInstance(courseId, Course_.id, Course.class, session, true, courseRootGraph);

        req.setAttribute("course", course);
        req.setAttribute("department", course.getDepartment());
        req.setAttribute("classRooms", course.getClassRooms());
        req.setAttribute("subjects", course1.getSubjects());

        var canDelete = course.getClassRooms().isEmpty()
                && course1.getSubjects().isEmpty();

        req.setAttribute("canDelete", canDelete);

        req.getRequestDispatcher("/WEB-INF/admin/course-details.jsp")
                .include(req, resp);

    }

}
