/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

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
@WebServlet(urlPatterns = "/admin/students/detailstudent")
public class DetailStudent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();

        try {
            int studentId = Integer.valueOf(req.getParameter("studentId"));
            Student student = (Student) session.get(Student.class, studentId);
            Login login = (Login) session.createCriteria(Login.class)
                    .add(Restrictions.eq("id", student.getId()))
                    .add(Restrictions.eq("type", UserType.Student.toString()))
                    .list()
                    .get(0);
            req.setAttribute("student", student);
            req.setAttribute("username", login.getUsername());
            req.getRequestDispatcher("/WEB-INF/admin/detailstudent.jsp").include(req, resp);
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

}
