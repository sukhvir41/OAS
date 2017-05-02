/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.student;

import entities.Login;
import entities.Student;
import entities.UserType;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/student/updatepassword")
public class UpdatePassword extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Student student = (Student) req.getSession().getAttribute("student");
        student = (Student) session.get(Student.class, student.getId());

        String oldPassword = req.getParameter("oldpassword");
        String newPassword = req.getParameter("newpassword");
        String renewPassword = req.getParameter("renewpassword");

        Login login = (Login) session.createCriteria(Login.class)
                .add(Restrictions.eq("type", UserType.Student.toString()))
                .add(Restrictions.eq("id", student.getId()))
                .list()
                .get(0);

        if (login.checkPassword(oldPassword)) {
            if (newPassword.length() >= 8 && newPassword.length() <= 40 && newPassword.equals(renewPassword)) {
                login.setPassword(newPassword);
                session.update(login);
                resp.sendRedirect("/OAS/student/changepassword?error=false");
            } else {
                resp.sendRedirect("/OAS/student/changepassword?error=true");
            }
        } else {
            resp.sendRedirect("/OAS/student/changepassword?error=true");
        }
    }

}
