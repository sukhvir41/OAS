/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.student;

import entities.Student;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/student/updatemacid")
public class UpdateMAacId extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
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
        resp.sendRedirect("/OAS/error");
    }

}
