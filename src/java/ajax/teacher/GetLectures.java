/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.teacher;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.Lecture;
import entities.Teacher;
import entities.Teaching;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/ajax/getlectures")
public class GetLectures extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());

        LocalDateTime startDate = Utils.getStartdate(req.getParameter("startdate"));
        LocalDateTime endDate = Utils.getEndDate(req.getParameter("enddate"));

        int teachingId = Integer.parseInt(req.getParameter("teachingId"));
        Teaching teaching = (Teaching) session.get(Teaching.class, teachingId);

        if (teaching.getTeacher().getId() == teacher.getId()) {
            Gson gson = new Gson();
            JsonArray jsonLectures = new JsonArray();

            session.createCriteria(Lecture.class)
                    .add(Restrictions.eq("teaching", teaching))
                    .add(Restrictions.between("date", startDate, endDate))
                    .addOrder(Order.desc("date"))
                    .list()
                    .stream()
                    .forEach(lecture -> add(jsonLectures, (Lecture) lecture));

            out.print(gson.toJson(jsonLectures));
        } else {
            out.print("error");
        }
    }

    private void add(JsonArray jsonLectures, Lecture theLecture) {

        int attendentStudent = theLecture.getAttendance()
                .stream()
                .filter(attendance -> attendance.isAttended())
                .collect(Collectors.toList())
                .size();
        int totalStudents = theLecture.getTeaching()
                .getClassRoom()
                .getStudents()
                .stream()
                .filter(student -> student.getSubjects().contains(theLecture.getTeaching().getSubject()))
                .collect(Collectors.toList())
                .size();

        JsonObject lecture = new JsonObject();
        lecture.addProperty("id", theLecture.getId());
        lecture.addProperty("class", theLecture.getTeaching().getClassRoom().toString());
        lecture.addProperty("subject", theLecture.getTeaching().getSubject().toString());
        lecture.addProperty("count", theLecture.getCount());
        lecture.addProperty("date", theLecture.getDate().toString());
        lecture.addProperty("present", attendentStudent);
        lecture.addProperty("absent", totalStudents - attendentStudent);

        jsonLectures.add(lecture);

    }
}
