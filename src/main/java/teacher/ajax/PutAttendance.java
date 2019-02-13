/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher.ajax;

import entities.*;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;

/**
 *
 * @author icr
 */
@WebServlet(urlPatterns = "/teacher/ajax/putattendance")
public class PutAttendance extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

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
            LocalDateTime lectureTime = lecture.getDate();
            LocalDateTime lectureOffsetTime = lectureTime.plusHours(3);

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
                    Attendance attend = new Attendance(new AttendanceId(lecture, student),true,mark,false);
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

    }

}
