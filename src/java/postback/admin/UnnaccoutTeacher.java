/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.Teacher;
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
@WebServlet(urlPatterns = "/admin/teachers/unaacountteacher")
public class UnnaccoutTeacher extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            int teacherId = Integer.parseInt(req.getParameter("teacherId"));
            Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);
            if (!teacher.isVerified()) {
                teacher.setClassRoom(null);
                teacher.getDepartment().stream()
                        .forEach(e -> e.getTeachers().remove(teacher));
                teacher.getHodOf().stream()
                        .forEach(e -> e.setHod(null));
                teacher.getTeaches().stream()
                        .forEach(e -> e.setTeacher(null));
                teacher.getTeaches().clear();
                teacher.unaccount();
            } 
               resp.sendRedirect("/OAS/admin/teachers/detailteacher?teacherId=" + teacherId);

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
