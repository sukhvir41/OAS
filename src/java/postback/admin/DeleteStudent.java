/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.Login;
import entities.Student;
import entities.UserType;
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
@WebServlet(urlPatterns = "/admin/students/deletestudent")
public class DeleteStudent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            int studentId = Integer.parseInt(req.getParameter("studentId"));
            Student student = (Student) session.get(Student.class, studentId);
            if (student.isUnaccounted()) {
                Login login = (Login) session.createCriteria(Student.class)
                        .add(Restrictions.eq("type", UserType.Student.toString()))
                        .add(Restrictions.eq("id", student.getId()))
                        .list()
                        .get(0);
                session.delete(login);
                session.delete(student);
                resp.sendRedirect("/OAS/admin/students");
            } else {
                resp.sendRedirect("/OAS/admin/students/detailstudent?studentId=" + studentId);
            }
            session.getTransaction().commit();
            session.close();
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

    }

}
