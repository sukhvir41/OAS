/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.Login;
import entities.Student;
import entities.Teacher;
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
@WebServlet("/admin/teachers/deleteteacher")
public class DeleteTeacher extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            int teacherId = Integer.parseInt(req.getParameter("teacherId"));
            Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);
            if (teacher.isUnaccounted()) {
                 Login login = (Login) session.createCriteria(Student.class)
                        .add(Restrictions.eq("type", UserType.Teacher.toString()))
                        .add(Restrictions.eq("id", teacher.getId()))
                        .list()
                        .get(0);
                session.delete(login);
                session.delete(teacher);
                resp.sendRedirect("/OAS/admin/teacherd");
            } else {
                resp.sendRedirect("/OAS/admin/teachers/detailteacher?teacherId=" + teacherId);
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
