/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.Course;
import java.io.IOException;
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
@WebServlet(urlPatterns = "/admin/courses/updatecourse")
public class UpdateCourse extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            int courseId = Integer.parseInt(req.getParameter("courseId"));
            String name = req.getParameter("coursename");
            
            Course course = (Course) session.get(Course.class, courseId);
            course.setName(name);
            session.update(course);
            session.getTransaction().commit();
            session.close();
            
            if (req.getParameter("from").equals("")) {
                resp.sendRedirect("/OAS/admin/courses/detailcourse?courseId=" + courseId);
            } else {
                resp.sendRedirect("/OAS/admin/courses#" + courseId);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            
            resp.sendRedirect("/OAS/error");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/admin/courses");
    }
    
}
