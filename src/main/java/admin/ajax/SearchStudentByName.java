/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entities.Student;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

import static utility.Constants.*;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/students/searchstudentbyname")
public class SearchStudentByName extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        resp.setContentType("application/json");

        String name = req.getParameter("name");
        List<Student> students = session.createCriteria(Student.class)
                .add(Restrictions.or(Restrictions.like("fName", "%" + name + "%"), Restrictions.like("lName", "%" + name + "%")))
                .list();

        JsonArray jsonStudents = new JsonArray();
        Gson gson = new Gson();
        students.stream()
                .forEach(student -> add(student, jsonStudents));

        out.print(gson.toJson(jsonStudents));

    }

    private void add(Student student, JsonArray jsonStudents) {
        JsonObject studentJson = new JsonObject();

        studentJson.addProperty(ID, student.getId().toString());
        studentJson.addProperty(NAME, student.toString());
        studentJson.addProperty(EMAIL, student.getUser().getEmail());
        studentJson.addProperty(NUMBER, student.getUser().getNumber());
        studentJson.addProperty(CLASSROOM, student.getClassRoom().getName() + " " + student.getClassRoom().getDivision());
        studentJson.addProperty(ROLLNUMBER, student.getRollNumber());
        //studentJson.addProperty(VERIFIED, student.isVerified());
        studentJson.add(SUBJECTS, addSubjects(student));

        jsonStudents.add(studentJson);
    }

    private JsonElement addSubjects(Student e) {
        //Todo: check search students for the prper way to do this 
        JsonArray jsonSubjects = new JsonArray();

        e.getSubjects()
                .forEach(subject -> jsonSubjects.add(subject.getSubject().getName()));

        return jsonSubjects;
    }

}
