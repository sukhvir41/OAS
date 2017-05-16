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
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.AjaxController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/searchstudent")
public class SearchStudent extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        resp.setContentType("text/json");

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
                        .filter(student -> !student.isUnaccounted())
                        .forEach(student -> add(student, jsonStudents));
            } else {
                classRoom.getStudents()
                        .stream()
                        .filter(student -> !student.isUnaccounted())
                        .filter(e -> e.isVerified() == Boolean.parseBoolean(filter))
                        .forEach(e -> add(e, jsonStudents));
            }
        } else {
            ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
            Subject subject = (Subject) session.get(Subject.class, Integer.parseInt(subjectId));
            if (filter.equals("all")) {
                classRoom.getStudents()
                        .stream()
                        .filter(student -> !student.isUnaccounted())
                        .filter(student -> student.getSubjects().contains(subject))
                        .forEach(student -> add(student, jsonStudents));
            } else {
                classRoom.getStudents()
                        .stream()
                        .filter(student -> !student.isUnaccounted())
                        .filter(student -> student.isVerified() == Boolean.parseBoolean(filter))
                        .filter(student -> student.getSubjects().contains(subject))
                        .forEach(e -> add(e, jsonStudents));
            }

        }
        out.print(gson.toJson(jsonStudents));

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

}
