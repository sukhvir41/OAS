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

    //###
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Session session = Utils.openSession();
        session.getTransaction().begin();
        try {
            int lectureId = Integer.parseInt(req.getParameter("lectureId"));
            int studentId = Integer.parseInt(req.getParameter("studentId"));
            Boolean mark = Boolean.valueOf(req.getParameter("mark"));
            Teacher teacher = (Teacher) req.getSession().getAttribute("teacher");
            teacher = (Teacher) session.get(Teacher.class, teacher.getId());
            Lecture lecture = (Lecture) session.get(Lecture.class, lectureId);
            Student student = (Student) session.get(Student.class, studentId);
            if (lecture.getTeaching().getTeacher().equals(teacher)) {
                Date now = new Date();
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.HOUR, 3);
                if (now.after(lecture.getDate()) && now.before(cal.getTime())) {
                    List<Attendance> attendance = session.createCriteria(Attendance.class)
                            .add(Restrictions.eq("lecture", lecture))
                            .add(Restrictions.eq("student", student))
                            .setFetchSize(1)//###
                            .list();
                    if (attendance.size() > 0) {
                        attendance.get(0).setAttended(mark);
                        attendance.get(0).setMarkedByTeacher(true);
                    } else {
                        Attendance attend = new Attendance(lecture, student);
                        attend.setAttended(mark);
                        attend.setMarkedByTeacher(true);
                        session.save(attend);
                        out.print("true");
                    }
                } else {
                    out.print("true");
                }

            } else {
                out.print("false");
            }
            session.getTransaction().rollback();
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
        //###
    }

}
