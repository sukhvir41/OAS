/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.teacher;

import entities.Attendance;
import entities.Lecture;
import entities.Student;
import entities.Teacher;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Calendar;
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
 * @author icr
 */
@WebServlet(urlPatterns = "/teacher/ajax/putattendance")
public class PutAttendance extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Session session = Utils.openSession();
        session.getTransaction().begin();
        try {
            String lectureId = req.getParameter("lectureId");
            int studentId = Integer.parseInt(req.getParameter("studentId"));
            Boolean mark = Boolean.valueOf(req.getParameter("mark"));
            Teacher teacher = (Teacher) req.getSession().getAttribute("teacher");
            teacher = (Teacher) session.get(Teacher.class, teacher.getId());
            Lecture lecture = (Lecture) session.get(Lecture.class, lectureId);
            Student student = (Student) session.get(Student.class, studentId);
            System.out.println(student + "   " + mark + "  " + lecture.getId());
            if (lecture.getTeaching().getTeacher().equals(teacher)) {
                LocalDateTime presentTime = LocalDateTime.now();
                LocalDateTime lectureOffsetTime = lecture.getDate();
                lectureOffsetTime.plusHours(3);
                if (presentTime.isAfter(lecture.getDate()) && presentTime.isBefore(lectureOffsetTime)) {
                    List<Attendance> attendance = session.createCriteria(Attendance.class)
                            .add(Restrictions.eq("lecture", lecture))
                            .add(Restrictions.eq("student", student))
                            .list();
                    if (attendance.size() > 0) {
                        attendance.get(0).setAttended(mark);
                        attendance.get(0).setMarkedByTeacher(true);
                        session.update(attendance.get(0));
                        System.out.println("marked already " + attendance.get(0).getStudent().toString());
                        out.print("true");
                    } else {
                        Attendance attend = new Attendance(lecture, student);
                        attend.setAttended(mark);
                        attend.setMarkedByTeacher(true);
                        session.save(attend);
                        System.out.println("marked");
                        out.print("true");
                    }
                } else {
                    System.out.println("time out");
                    out.print("false");
                }

            } else {
                out.print("false");
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
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
