/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.hod;

import entities.Student;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.AjaxController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/hod/ajax/activatedeactivatestudent")
public class ActivateOrDeactivateStudent extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int studentId = Integer.parseInt(req.getParameter("studentId"));
        String action = req.getParameter("action");
        Student student = (Student) session.get(Student.class, studentId);
        switch (action) {
            case "verify": {
                student.setVerified(true);
                out.print("true");
                break;
            }
            case "deverify": {
                student.setVerified(false);
                out.print("true");
                break;
            }
            default: {
                out.print("false");
            }
        }
    }

}
