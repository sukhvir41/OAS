/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.admin;

import entities.Teacher;
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
@WebServlet(urlPatterns = "/admin/ajax/teachers/activateordeactivateteacher")
public class ActivateOrDeactivateTeacher extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Session session = Utils.openSession();
        session.beginTransaction();
        PrintWriter out = resp.getWriter();

        try {
            String action = req.getParameter("action");
            int teacherId = Integer.parseInt(req.getParameter("teacherId"));
            Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);
            switch (action) {
                case "verify": {
                    teacher.setVerified(true);
                    out.print("true");
                    break;
                }
                case "deverify": {
                    teacher.setVerified(false);
                    out.print("true");
                    break;
                }
                default:
                    out.print("false");
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.print("false");
            session.getTransaction().rollback();
            session.close();
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