/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Teacher;
import entities.Teaching;
import java.io.IOException;
import java.util.List;
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
@WebServlet(urlPatterns = "/admin/teachers/detailteacher")
public class AdminTeacherDetails extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int teacherId;
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            teacherId = Integer.parseInt(req.getParameter("teacherId"));
            Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);
            req.setAttribute("teacher", teacher);
            List<Teaching> teachings = session.createCriteria(Teaching.class)
                    .add(Restrictions.isNotNull("classRoom"))
                    .add(Restrictions.isNotNull("subject"))
                    .add(Restrictions.eq("teacher", teacher))
                    .list();
            req.setAttribute("details", teachings);
            req.getRequestDispatcher("/WEB-INF/admin/detailteacher.jsp").forward(req, resp);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();

            resp.sendRedirect("/OAS/error");
        }
    }

}
