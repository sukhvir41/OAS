/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classteacher.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entities.Student;
import entities.Teacher;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;
import static utility.Constants.*;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/classteacher/ajax/searchstudentbyname")
public class SearchStudentByName extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());

        String name = req.getParameter("name");

        List<Student> students = session.createCriteria(Student.class)
                .add(Restrictions.or(Restrictions.like("fName", "%" + name + "%"), Restrictions.like("lName", "%" + name + "%")))
                .add(Restrictions.eq("classRoom", teacher.getClassRoom()))
                .list();

        JsonArray jsonStudents = new JsonArray();
        Gson gson = new Gson();
        students.stream()
                .sorted()
                .forEach(student -> add(student, jsonStudents));
        out.print(gson.toJson(jsonStudents));

    }

    private void add(Student student, JsonArray jsonStudents) {
        JsonObject studentJson = new JsonObject();

        studentJson.addProperty(ID, student.getId());
        studentJson.addProperty(NAME, student.toString());
        studentJson.addProperty(EMAIL, student.getEmail());
        studentJson.addProperty(NUMBER, student.getNumber());
        studentJson.addProperty(CLASSROOM, student.getClassRoom().getName() + " " + student.getClassRoom().getDivision());
        studentJson.addProperty(ROLLNUMBER, student.getRollNumber());
        studentJson.addProperty(VERIFIED, student.isVerified());
        studentJson.add(SUBJECTS, addSubjects(student));

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
