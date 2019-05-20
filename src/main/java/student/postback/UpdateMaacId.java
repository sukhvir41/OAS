/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.postback;

import entities.Student;
import org.hibernate.Session;
import utility.PostBackController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/student/updatemacid")
public class UpdateMaacId extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        String macId = req.getParameter("macid");
        Student student = (Student) req.getSession().getAttribute("student");
        student = (Student) session.get(Student.class, student.getId());
        if ((macId != null && !macId.equals("")) && !macId.equalsIgnoreCase(student.getMacId())) {
            if (student.getMacId() == null) {
                student.setMacId(macId);
                resp.sendRedirect("/OAS/student/resetmacid");
            } else {
                student.setMacId(macId);
                student.setVerified(false);
                resp.sendRedirect("/OAS/logout");
            }
            req.getSession().setAttribute("student", student);
        } else {
            resp.sendRedirect("/OAS/student/resetmacid");
        }

    }
}
