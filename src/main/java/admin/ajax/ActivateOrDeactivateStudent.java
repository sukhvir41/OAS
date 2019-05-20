/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.ajax;

import entities.Student;
import org.hibernate.Session;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * have to fix this and have to figure out what to when student is deactivated. 
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/students/activateordeactivatestudent")
public class ActivateOrDeactivateStudent extends AjaxController {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        
        long studentId = Long.parseLong(req.getParameter("studentId"));
        String action = req.getParameter("action");
        Student student = (Student) session.get(Student.class, studentId);
        student.setUnaccounted(false);
        switch (action) {
            case "verify": {
                student.setVerified(true);
                out.print(true);
                break;
            }
            case "deverify": {
                student.setVerified(false);
                out.print(true);
                break;
            }
            default: {
                out.print(false);
            }
        }
        
    }
    
}
