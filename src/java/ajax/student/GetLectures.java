/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.student;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.Attendance;
import entities.Lecture;
import entities.Student;
import entities.Subject;
import entities.Teaching;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/student/ajax/getlectures")
public class GetLectures extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Student student = (Student) req.getSession().getAttribute("student");
        Student currentStudent = (Student) session.get(Student.class, student.getId());

        int subjectId = Integer.parseInt(req.getParameter("subjectId"));

        Subject subject = (Subject) session.get(Subject.class, subjectId);

        if (currentStudent.getSubjects().contains(subject)) {
            Teaching teaching = (Teaching) session.createCriteria(Teaching.class)
                    .add(Restrictions.eq("subject", subject))
                    .add(Restrictions.eq("classRoom", currentStudent.getClassRoom()))
                    .list()
                    .get(0);

            List<Lecture> lectures = session.createCriteria(Lecture.class)
                    .add(Restrictions.eq("teaching", teaching))
                    //.add   add date here
                    .addOrder(Order.desc("date"))
                    .list();

            Gson gson = new Gson();
            JsonArray jsonLectures = new JsonArray();

            lectures.stream()
                    .forEach(lecture -> add(lecture, currentStudent, jsonLectures));
            
            out.print(gson.toJson(jsonLectures));

        } else {
            out.print("error");
        }
    }

    private void add(Lecture theLecture, Student student, JsonArray jsonLectures) {
        JsonObject lecture = new JsonObject();
        lecture.addProperty("id", theLecture.getId());
        lecture.addProperty("class", theLecture.getTeaching().getClassRoom().toString());
        lecture.addProperty("subject", theLecture.getTeaching().getSubject().toString());
        lecture.addProperty("count", theLecture.getCount());
        lecture.addProperty("date", theLecture.getDate().toString());

        List<Attendance> attendaces = theLecture.getAttendance()
                .stream()
                .filter(attendance -> attendance.getStudent().getId() == student.getId())
                .collect(Collectors.toList());
        System.out.println(attendaces.size());
        attendaces.stream()
                .forEach(attendace -> System.out.println(attendace.isAttended() + "   "+ attendace.getLecture().getId() +  "    " +attendace.getStudent().toString()+ "    "+ attendace.getStudent().getId()));
        if (attendaces.size() > 0) {
            if (attendaces.get(0).isAttended()) {
                lecture.addProperty("status", "present");
            } else {
                lecture.addProperty("status", "absent");
            }
        } else {
            lecture.addProperty("status", "absent");
        }
        
        jsonLectures.add(lecture);
    }

}
