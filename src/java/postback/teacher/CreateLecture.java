/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.teacher;

import entities.ClassRoom;
import entities.Lecture;
import entities.Subject;
import entities.Teacher;
import entities.Teaching;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/createlecture")
public class CreateLecture extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int count;
        int classroomId;
        int subjectId;
        Session session = Utils.openSession();
        session.beginTransaction();

        try {
            count = Integer.parseInt(req.getParameter("count"));
            classroomId = Integer.parseInt(req.getParameter("classroomId"));
            subjectId = Integer.parseInt(req.getParameter("subjectId"));
            Teacher teacher = (Teacher) req.getSession().getAttribute("teacher");
            teacher = (Teacher) session.get(Teacher.class, teacher.getId());
            ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
            Subject subject = (Subject) session.get(Subject.class, subjectId);
            Teaching teaching = (Teaching) session.createCriteria(Teaching.class)
                    .add(Restrictions.eq("teacher", teacher))
                    .add(Restrictions.eq("classRoom", classRoom))
                    .add(Restrictions.eq("subject", subject))
                    .list().get(0);
            Lecture lecture = (Lecture) session.createCriteria(Lecture.class)
                    .add(Restrictions.eq("teaching", teaching))
                    .addOrder(Order.desc("date"))
                    .setMaxResults(1)
                    .list().get(0);
            Date now = new Date();

            
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
        } finally {
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/error");
    }

}
