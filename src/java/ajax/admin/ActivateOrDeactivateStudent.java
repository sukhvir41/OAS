/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.admin;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;
import entities.Student;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(urlPatterns = "/admin/ajax/students/activateordeactivatestudent")
public class ActivateOrDeactivateStudent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Session session = Utils.openSession();
        session.beginTransaction();

        try {
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
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            out.print("false");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("error");
        resp.getWriter().close();
    }

}
