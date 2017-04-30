/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.hod;

import entities.Course;
import entities.Department;
import java.io.PrintWriter;
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
@WebServlet(urlPatterns = "/teacher/hod/courses/detailcourse")
public class DetailCourse extends Controller {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int courseId = Integer.parseInt(req.getParameter("courseId"));
        
        Course course = (Course) session.get(Course.class, courseId);
        
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());
        
        if (course.getDepartment().getId() == department.getId()) {
            req.setAttribute("course", course);
            req.getRequestDispatcher("/WEB-INF/hod/detailcourse.jsp").include(req, resp);
        } else {
            resp.sendRedirect("/OAS/error");
        }
    }
    
}
