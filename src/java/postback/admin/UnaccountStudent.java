/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.Attendance;
import entities.Student;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/students/unaccountstudent")
public class UnaccountStudent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            int studentId = Integer.parseInt(req.getParameter("studentId"));
            Student student = (Student) session.get(Student.class, studentId);
            if (!student.isVerified()) {
                student.getClassRoom().getStudents().remove(student);
                student.setClassRoom(null);
                student.getSubjects().stream()
                        .forEach(e -> e.getStudents().remove(student));
                student.getSubjects().clear();
                student.getAttendance().stream()
                        .forEach(e -> e.setStudent(null));
                student.getAttendance().clear();
                session.createCriteria(Attendance.class)
                        .add(Restrictions.isNull("student"))
                        .list()
                        .stream()
                        .forEach(e -> session.delete(e));
                student.unaccount();
            }
            session.getTransaction().commit();
            session.close();
            resp.sendRedirect("/OAS/admin/students/detailsstudent?studentId=" + studentId);
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
            resp.sendRedirect("/OAS/error");
        } finally {
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/error");
    }

}
