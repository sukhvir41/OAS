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
import entities.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
@WebServlet(urlPatterns = "/teacher/getattendance")
public class getAttendance extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Session session = Utils.openSession();
        session.beginTransaction();
        resp.setContentType("text/json");
        try {
            String lectureId = req.getParameter("lectureId");
            Lecture lecture = (Lecture) session.get(Lecture.class, lectureId);
            List<Student> students = new ArrayList<>();
            lecture.getTeaching().getClassRoom()
                    .getStudents()
                    .stream()
                    .filter(e -> e.getSubjects().contains(lecture.getTeaching().getSubject()))
                    .forEach(e -> students.add(e));

            List<Student> present = new ArrayList<>();
            lecture.getAttendance().stream()
                    .filter(e -> e.isAttended())
                    .forEach(e -> present.add(e.getStudent()));
            students.removeAll(present);
            lecture.getAttendance().stream()
                    .forEach(e -> System.out.println(e.getStudent().toString() + "  " + e.isAttended()));
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
            students.removeAll(present);
            JsonObject main = new JsonObject();
            JsonArray pjson = new JsonArray();
            JsonArray ajson = new JsonArray();
            present.stream()
                    .forEach(e -> add(pjson, e));
            students.stream()
                    .forEach(e -> add(ajson, e));
            main.add("present", pjson);
            main.add("absent", ajson);
            Gson g = new Gson();
            out.print(g.toJson(main));
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
            out.print("error");
        } finally {
            out.close();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("error");
        resp.getWriter().close();
    }

    private void add(JsonArray json, Student e) {
        JsonObject object = new JsonObject();
        object.addProperty("rollNumber", e.getRollNumber());
        object.addProperty("name", e.toString());
        object.addProperty("id", e.getId());
        json.add(object);
    }
}
