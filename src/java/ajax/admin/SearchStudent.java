/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.admin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entities.ClassRoom;
import entities.Student;
import entities.Subject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/searchstudent")
public class SearchStudent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Session session = Utils.openSession();
        session.beginTransaction();
        resp.setContentType("text/json");
        try {
            int classroomId = Integer.parseInt(req.getParameter("classroom"));
            String subjectId = req.getParameter("subject");
            String filter = req.getParameter("filter");
            Gson gson = new Gson();
            JsonArray jsonStudents = new JsonArray();
            if (subjectId.equals("all")) {
                ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
                if (filter.equals("all")) {
                    classRoom.getStudents()
                            .stream()
                            .forEach(e -> add(e, jsonStudents));
                } else {
                    classRoom.getStudents()
                            .stream()
                            .filter(e -> e.isVerified() == Boolean.parseBoolean(filter))
                            .forEach(e -> add(e, jsonStudents));
                }
            } else {
                ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
                Subject subject = (Subject) session.get(Subject.class, Integer.parseInt(subjectId));
                if (filter.equals("all")) {
                    classRoom.getStudents()
                            .stream()
                            .filter(student -> student.getSubjects().contains(subject))
                            .forEach(student -> add(student, jsonStudents));
                } else {
                    classRoom.getStudents()
                            .stream()
                            .filter(e -> e.isVerified() == Boolean.parseBoolean(filter))
                            .filter(e -> e.getSubjects().contains(subject))
                            .forEach(e -> add(e, jsonStudents));
                }

            }
            out.print(gson.toJson(jsonStudents));
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            out.print("error");
        } finally {
            out.close();
        }
    }

    private void add(Student student, JsonArray jsonStudents) {
        JsonObject studentJson = new JsonObject();
        studentJson.addProperty("id", student.getId());
        studentJson.addProperty("name", student.toString());
        studentJson.addProperty("email", student.getEmail());
        studentJson.addProperty("number", student.getNumber());
        studentJson.addProperty("classroom", student.getClassRoom().getName() + " " + student.getClassRoom().getDivision());
        studentJson.addProperty("rollnumber", student.getRollNumber());
        studentJson.addProperty("verified", student.isVerified());
        studentJson.add("subjects", addSubjects(student));
        jsonStudents.add(studentJson);
    }

    private JsonElement addSubjects(Student e) {
        JsonArray jsonSubjects = new JsonArray();
        e.getSubjects()
                .stream()
                .forEach(subject -> jsonSubjects.add(subject.getName()));
        return jsonSubjects;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("error");
        resp.getWriter().close();
    }

}
