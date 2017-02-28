/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.ClassRoom;
import entities.Course;
import java.io.IOException;
import java.util.Enumeration;
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
@WebServlet(urlPatterns = "/admin/classrooms/addclassroom")
public class AddClassRoom extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {

            int courseId = Integer.parseInt(req.getParameter("courseId"));
            String name = req.getParameter("classroomname");
            String division = req.getParameter("division");
            int semister = Integer.parseInt(req.getParameter("semister"));
            int minimum = Integer.parseInt(req.getParameter("minimumsubjects"));

            ClassRoom classRoom = new ClassRoom(name, division, semister, minimum);

            Course course = (Course) session.get(Course.class, courseId);
            course.addClassRoom(classRoom);
            session.save(classRoom);
            session.update(course);
            session.getTransaction().commit();
            session.close();

            if (req.getParameter("from") == null) {
                resp.sendRedirect("/OAS/admin/classrooms");
            } else {
                resp.sendRedirect("/OAS/admin/courses/detailcourse?courseId=" + courseId);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            resp.sendRedirect("/OAS/admin");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/admin");
    }

}
