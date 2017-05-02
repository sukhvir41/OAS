/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.classteacher;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entities.Student;
import entities.Teacher;
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
@WebServlet(urlPatterns = "/teacher/classteacher/ajax/getstudents")
public class GetStudents extends AjaxController {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        String filter = req.getParameter("filter");
        
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());
        
        Gson gson = new Gson();
        JsonArray jsonStudent = new JsonArray();
        
        if (filter.equals("all")) {
            teacher.getClassRoom()
                    .getStudents()
                    .stream()
                    .sorted()
                    .forEach(student -> add(student, jsonStudent));
            
        } else {
            teacher.getClassRoom()
                    .getStudents()
                    .stream()
                    .filter(student -> student.isVerified() == Boolean.parseBoolean(filter))
                    .sorted()
                    .forEach(student -> add(student, jsonStudent));
        }
        
        out.print(gson.toJson(jsonStudent));
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
