/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.teacher;

import entities.Lecture;
import entities.Student;
import entities.Teacher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
@WebServlet(urlPatterns = {"/teacher"})
public class CreateLectureHome extends HttpServlet {

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
        String lectureId = req.getParameter("lectureId");

        try {
            Teacher teacher = (Teacher) req.getSession().getAttribute("teacher");
            teacher = (Teacher) session.get(Teacher.class, teacher.getId());
            req.setAttribute("teaching", teacher.getTeaches());
            Date now = new Date();
            Calendar past = Calendar.getInstance();
            past.add(Calendar.HOUR, -3);
            Lecture lecture;
            if (lectureId != null) {

                lecture = (Lecture) session.get(Lecture.class, lectureId);
                if (teacher.getTeaches().contains(lecture.getTeaching())) {
                    List<Lecture> lectures = session.createCriteria(Lecture.class)
                            .add(Restrictions.in("teaching", teacher.getTeaches()))
                            .add(Restrictions.between("date", past.getTime(), now))
                            .addOrder(Order.desc("date"))
                            .list();
                    lectures.remove(lecture);
                    req.setAttribute("active", lecture);
                    req.setAttribute("recent", lectures);
                    List<Student> present = session.createCriteria(Student.class)
                            .add(Restrictions.in("attendance", lecture.getAttendance().stream()
                                    .filter(e -> e.isAttended())
                                    .toArray()))
                            .list();
                    List<Student> absent = lecture.getTeaching().getClassRoom().getStudents();
                    absent.removeAll(present);
                    req.setAttribute("present", present);
                    req.setAttribute("absent", absent);
                } else {
                    resp.sendRedirect("/OAS/error");
                }
            } else {
                List<Lecture> lectures = new ArrayList<>();
                if (teacher.getTeaches().size() > 0) {
                    lectures = session.createCriteria(Lecture.class)
                            .add(Restrictions.in("teaching", teacher.getTeaches()))
                            .add(Restrictions.between("date", past.getTime(), now))
                            .addOrder(Order.desc("date")).
                            list();
                }

                if (lectures.size() > 0) {
                    lecture = lectures.get(0);
                    lectures.remove(lecture);
                    req.setAttribute("active", lecture);
                    req.setAttribute("recent", lectures);
                    List<Student> present = session.createCriteria(Student.class)
                            .add(Restrictions.in("attendance", lecture.getAttendance().stream().filter(e -> e.isAttended()).toArray()))
                            .list();
                    List<Student> absent = lecture.getTeaching().getClassRoom().getStudents();
                    absent.removeAll(present);
                    req.setAttribute("present", present);
                    req.setAttribute("absent", absent);
                }
            }
            req.getRequestDispatcher("/WEB-INF/teacher/teacherhome.jsp").forward(req, resp);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
            resp.sendRedirect("/OAS/error");
        }

    }

}
