/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.Attendance;
import entities.Student;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.PostBackController;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/students/unaccountstudent")
public class UnaccountStudent extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        int studentId = Integer.parseInt(req.getParameter("studentId"));
        Student student = (Student) session.get(Student.class, studentId);
        if (!student.isVerified()) {
            student.getClassRoom().getStudents().remove(student);
            student.setClassRoom(null);
            student.getSubjects().stream()
                    .forEach(e -> e.getStudents().remove(student));
            student.getSubjects().clear();
            //student.getAttendance().stream()
            //        .forEach(e -> e.setStudent(null));
            student.getAttendance().clear();
            session.createCriteria(Attendance.class)
                    .add(Restrictions.isNull("student"))
                    .list()
                    .stream()
                    .forEach(e -> session.delete(e));
            student.unaccount();
        }

        resp.sendRedirect("/OAS/admin/students/detailsstudent?studentId=" + studentId);

    }

}
