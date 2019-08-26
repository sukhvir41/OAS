/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.Lecture;
import entities.Student;
import org.hibernate.Session;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/getattendance")
public class GetAttendance extends AjaxController {

    private void add(JsonArray json, Student e) {
        JsonObject object = new JsonObject();
        object.addProperty("rollNumber", e.getRollNumber());
        object.addProperty("name", e.toString());
        object.addProperty("id", e.getId().toString());
        json.add(object);
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        resp.setContentType("text/json");

        String lectureId = req.getParameter("lectureId");
        Lecture lecture = (Lecture) session.get(Lecture.class, lectureId);
        List<Student> students = new ArrayList<>();
        lecture.getTeaching().getClassRoom()
                .getStudents()
                .stream()
                .filter(e -> e.getSubjects().contains(lecture.getTeaching().getSubject()))
                .forEach(e -> students.add(e));
        Collections.sort(students);
        List<Student> present = new ArrayList<>();
        // todo: write sql for this
        /* lecture.getAttendance().stream()
                .filter(e -> e.isAttended())
                .forEach(e -> present.add(e.getStudent()));
        students.removeAll(present);
        Collections.sort(present);*/
//            List<Student> absent = session.createCriteria(Student.class)
//                    .add(Restrictions.eq("classRoom", lecture.getTeaching().getClassRoom()))
//                    .addOrder(Order.asc("rollNumber"))
//                    .list();
//            
//            
//            List<Student> present = new ArrayList<>();
//            if (lecture.getAttendance().size() > 0) {
//                present = session.createCriteria(Student.class)
//                        .add(Restrictions.in("attendance", lecture.getAttendance().stream().filter(e -> e.isAttended()).toArray()))
//                        .addOrder(Order.asc("rollNumber"))
//                        .list();
//            }
        JsonObject main = new JsonObject();
        JsonArray pjson = new JsonArray();
        JsonArray ajson = new JsonArray();
        present.stream()
                .forEach(e -> add(pjson, e));
        students.stream()
                .forEach(e -> add(ajson, e));
        main.add("present", pjson);
        main.add("absent", ajson);
        main.addProperty("headcount", present.size());
        Gson g = new Gson();
        out.print(g.toJson(main));

    }
}
