/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.ClassRoom;
import entities.Course;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/classrooms/addclassroom")
public class AddClassRoom extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        long courseId = Long.parseLong(req.getParameter("courseId"));
        String name = req.getParameter("classroomname");
        String division = req.getParameter("division");
        int semister = Integer.parseInt(req.getParameter("semister"));
        int minimum = Integer.parseInt(req.getParameter("minimumsubjects"));

        ClassRoom classRoom = new ClassRoom(name, division, semister, minimum);

        Course course = (Course) session.get(Course.class, courseId);
        course.addClassRoom(classRoom);
        session.save(classRoom);
        session.update(course);

        if (req.getParameter("from") == null) {
            resp.sendRedirect("/OAS/admin/classrooms");
        } else {
            resp.sendRedirect("/OAS/admin/courses/detailcourse?courseId=" + courseId);
        }

    }

}
