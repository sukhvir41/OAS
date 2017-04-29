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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime past = now.minusHours(3);
            Lecture lecture;
            if (lectureId != null) {

                lecture = (Lecture) session.get(Lecture.class, lectureId);
                if (teacher.getTeaches().contains(lecture.getTeaching())) {
                    List<Lecture> lectures = session.createCriteria(Lecture.class)
                            .add(Restrictions.in("teaching", teacher.getTeaches()))
                            .add(Restrictions.between("date", past, now))
                            .addOrder(Order.desc("date"))
                            .list();
                    lectures.remove(lecture);
                    req.setAttribute("active", lecture);
                    req.setAttribute("recent", lectures);

                    List<Student> students = new ArrayList<>();
                    lecture.getTeaching().getClassRoom()
                            .getStudents()
                            .stream()
                            .filter(e -> e.getSubjects().contains(lecture.getTeaching().getSubject()))
                            .forEach(e -> students.add(e));
                    Collections.sort(students);
                    List<Student> present = new ArrayList<>();
                    lecture.getAttendance().stream()
                            .filter(e -> e.isAttended())
                            .forEach(e -> present.add(e.getStudent()));
                    students.removeAll(present);
                    Collections.sort(present);
//                    List<Student> present = new ArrayList<>();
//                    if (lecture.getAttendance().size() > 0) {
//                        present = session.createCriteria(Student.class)
//                                .add(Restrictions.in("attendance", lecture.getAttendance().stream()
//                                        .filter(e -> e.isAttended())
//                                        .toArray()))
//                                .list();
//                    }
//                    List<Student> absent = lecture.getTeaching().getClassRoom().getStudents();
//                    absent.removeAll(present);
                    req.setAttribute("present", present);
                    req.setAttribute("absent", students);
                    req.setAttribute("headcount", present.size());
                } else {
                    resp.sendRedirect("/OAS/error");
                }
            } else {
                List<Lecture> lectures = new ArrayList<>();
                if (teacher.getTeaches().size() > 0) {
                    lectures = session.createCriteria(Lecture.class)
                            .add(Restrictions.in("teaching", teacher.getTeaches()))
                            .add(Restrictions.between("date", past, now))
                            .addOrder(Order.desc("date")).
                            list();
                }

                if (lectures.size() > 0) {
                    lecture = lectures.get(0);
                    lectures.remove(lecture);
                    req.setAttribute("active", lecture);
                    req.setAttribute("recent", lectures);

                    List<Student> students = new ArrayList<>();
                    lecture.getTeaching().getClassRoom()
                            .getStudents()
                            .stream()
                            .filter(student -> student.getSubjects().contains(lecture.getTeaching().getSubject()))
                            .forEach(student -> students.add(student));
                    Collections.sort(students);
                    List<Student> present = new ArrayList<>();
                    lecture.getAttendance().stream()
                            .filter(e -> e.isAttended())
                            .forEach(e -> present.add(e.getStudent()));
                    students.removeAll(present);
                    Collections.sort(present);
//                    List<Student> present = new ArrayList<>();
//                    if (lecture.getAttendance().size() > 0) {       have to chekcth uis why it is removed
//                        List<Attendance> temp = new ArrayList<>();
//                        lecture.getAttendance().stream()
//                                .filter(e -> e.isAttended())
//                                .forEach(e -> temp.add(e));
//                        if (temp.size() > 0) {
//                            present = session.createCriteria(Student.class)
//                                    .add(Restrictions.in("attendance", temp))
//                                    .list();
//                        }
//                    }
//                    List<Student> absent = lecture.getTeaching().getClassRoom().getStudents();
//                    absent.removeAll(present);
                    req.setAttribute("present", present);
                    req.setAttribute("absent", students);
                    req.setAttribute("headcount", present.size());
                }
            }
            req.getRequestDispatcher("/WEB-INF/teacher/teacherhome.jsp").include(req, resp);
            session.getTransaction().commit();
            session.close();
            extendCookie(req, resp);
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
            resp.sendRedirect("/OAS/error");
        }

    }

    private void extendCookie(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        try {
            if ((boolean) session.getAttribute("extenedCookie")) {
                session.setAttribute("extenedCookie", false);
                Cookie id = null, token = null;

                for (Cookie cookie : req.getCookies()) {
                    switch (cookie.getName()) {
                        case "sid":
                            id = cookie;
                            break;
                        case "stoken":
                            token = cookie;
                            break;
                    }
                }

                id.setMaxAge(864000);
                token.setMaxAge(864000);
                resp.addCookie(id);
                resp.addCookie(token);
            }

        } catch (Exception e) {
            System.out.println("error in extend cookie");
        }
    }
}
