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
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/student/updatepassword")
public class UpdatePassword extends PostBackController {

    @Override
    public void process(
            HttpServletRequest req,
            HttpServletResponse resp,
            Session session,
            HttpSession httpSession,
            PrintWriter out) throws Exception {
        Student student = (Student) req.getSession().getAttribute("student");
        student = (Student) session.get(Student.class, student.getId());

        String oldPassword = req.getParameter("oldpassword");
        String newPassword = req.getParameter("newpassword");
        String renewPassword = req.getParameter("renewpassword");

        if (student.getUser().checkPassword(oldPassword)) {
            if (newPassword.length() >= 8 && newPassword.length() <= 40 && newPassword.equals(renewPassword)) {
                student.getUser().setPassword(newPassword);
                session.update(student);
                resp.sendRedirect("/OAS/student/changepassword?error=false");
            } else {
                resp.sendRedirect("/OAS/student/changepassword?error=true");
            }
        } else {
            resp.sendRedirect("/OAS/student/changepassword?error=true");
        }
    }

}
