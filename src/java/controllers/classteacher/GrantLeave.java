/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.classteacher;

import entities.Student;
import entities.Teacher;
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
@WebServlet(urlPatterns = "/teacher/classteacher/grantleave")
public class GrantLeave extends Controller {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int studentId = Integer.parseInt(req.getParameter("studentId"));
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());
        Student student = (Student) session.get(Student.class, studentId);
        if (student.getClassRoom().getId() == teacher.getClassRoom().getId()) {
            req.setAttribute("student", student);
            req.getRequestDispatcher("/WEB-INF/classteacher/teachergrantleave.jsp").include(req, resp);
        } else {
            resp.sendRedirect("/OAS/accessdenied.jsp");
        }
    }
    
}
