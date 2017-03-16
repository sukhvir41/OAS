/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.Attendance;
import entities.Lecture;
import entities.Student;
import entities.Teaching;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(urlPatterns = "/admin/students/grantleavepost")
public class GrantLeavesPost extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            int studentId = Integer.parseInt(req.getParameter("studentId"));
            //Date start = new SimpleDateFormat("yyyy-mm-dd").parse(req.getParameter("startdate"));
            //Date end = new SimpleDateFormat("yyyy-mm-dd").parse(req.getParameter("enddate"));
            Student student = (Student) session.get(Student.class, studentId);
            List<Teaching> teachings = session.createCriteria(Teaching.class)
                    .add(Restrictions.eq("classRoom", student.getClassRoom()))
                    .add(Restrictions.in("subject", student.getSubjects()))
                    .list();
            List<Lecture> lectures = null;
            if (!teachings.isEmpty()) {
                lectures = session.createCriteria(Lecture.class)
                        .add(Restrictions.in("teaching", teachings))
                        .list();

            }
            List<Attendance> attendances = student.getAttendance();
            
            
            
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
        //resp.sendRedirect("/OAS/error");
        doPost(req, resp);
    }

}
