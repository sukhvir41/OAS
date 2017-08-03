/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.Attendance;
import entities.ClassRoom;
import entities.Lecture;
import entities.Student;
import entities.Subject;
import entities.Teaching;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;
import utility.Utils;
import static utility.Constants.*;
/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/searchlecture")
public class SearchLectures extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        int classId = Integer.parseInt(req.getParameter("classroomId"));
        String subjectId = req.getParameter("subjectId");
        LocalDateTime startDate = Utils.getStartdate(req.getParameter("startdate"));
        LocalDateTime endDate = Utils.getEndDate(req.getParameter("enddate"));

        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classId);

        List<Teaching> teaching;

        if (subjectId.equals("all")) {
            teaching = session.createCriteria(Teaching.class)
                    .add(Restrictions.eq("classRoom", classRoom))
                    .list();

        } else {
            Subject subject = (Subject) session.get(Subject.class, Integer.parseInt(subjectId));
            teaching = session.createCriteria(Teaching.class)
                    .add(Restrictions.eq("subject", subject))
                    .add(Restrictions.eq("classRoom", classRoom))
                    .list();

        }

        List<Lecture> lectures = session.createCriteria(Lecture.class)
                .add(Restrictions.in("teaching", teaching))
                .add(Restrictions.between("date", startDate, endDate))
                .addOrder(Order.desc("date"))
                .list();

        JsonArray jsonLectures = new JsonArray();

        lectures.forEach(lecture -> add(jsonLectures, lecture, session));

        Gson gson = new Gson();
        out.print(gson.toJson(jsonLectures));

    }

    private void add(JsonArray jsonLectures, Lecture theLecture, Session session) {

        int attendedStudent = (int) session.createCriteria(Attendance.class)
                .add(Restrictions.eq("lecture", theLecture))
                .add(Restrictions.eq("attended", true))
                .setProjection(Projections.rowCount())
                .uniqueResult();

//        int attendedStudent = theLecture.getAttendance()
//                .stream()
//                .filter(attendance -> attendance.isAttended())
//                .collect(Collectors.toList())
//                .size();
//        
        //todo: have to optimize this.
        int totalStudents = theLecture.getTeaching()
                .getClassRoom()
                .getStudents()
                .stream()
                .filter(student -> student.getSubjects().contains(theLecture.getTeaching().getSubject()))
                .collect(Collectors.toList())
                .size();

        JsonObject lecture = new JsonObject();
        lecture.addProperty(ID, theLecture.getId());
        lecture.addProperty(CLASS, theLecture.getTeaching().getClassRoom().toString());
        lecture.addProperty(SUBJECT, theLecture.getTeaching().getSubject().toString());
        lecture.addProperty(COUNT, theLecture.getCount());
        lecture.addProperty(DATE, Utils.formatDateTime(theLecture.getDate()));
        lecture.addProperty(PRESENT, attendedStudent);
        lecture.addProperty(ABSENT, totalStudents - attendedStudent);

        jsonLectures.add(lecture);

    }

}
