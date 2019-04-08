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

import entities.Course;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/courses/updatecourse")
public class UpdateCourse extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        int courseId = Integer.parseInt(req.getParameter("courseId"));
        String name = req.getParameter("coursename");

        Course course = (Course) session.get(Course.class, courseId);
        course.setName(name);
        session.update(course);

        if (req.getParameter("from").equals("")) {
            resp.sendRedirect("/OAS/admin/courses/detailcourse?courseId=" + courseId);
        } else {
            resp.sendRedirect("/OAS/admin/courses#" + courseId);
        }

    }

}
