/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classteacher.ajax;

import entities.Student;
import entities.Teacher;
import org.hibernate.Session;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/classteacher/ajax/activatedeactivateteacher")
public class ActivateOrDeactivateStudent extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());

        int studentId = Integer.parseInt(req.getParameter("studentId"));
        String action = req.getParameter("action");

        Student student = (Student) session.get(Student.class, studentId);

       /* if (teacher.getClassRoom().getStudents().contains(student)) {
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
                    out.print(ERROR);
                }
            }
        } else {
            out.print(ERROR);
        }*/
    }

}
