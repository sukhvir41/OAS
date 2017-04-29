/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Student;
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
@WebServlet(urlPatterns = "/admin/students/unaccountteacher")
public class UnaccountStudent extends Controller {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int studentId = Integer.parseInt(req.getParameter("studentId"));
        Student student = (Student) session.get(Student.class, studentId);
        
        if (student.isVerified()) {
            resp.sendRedirect("/OAS/admin/students/detailstudent?studentId=" + studentId);
        } else {
            student.setUnaccounted(true);
            resp.sendRedirect("/OAS/admin/students");
        }
    }
    
}
