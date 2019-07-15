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
import entities.ClassRoom;
import entities.Student;
import entities.Subject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import static utility.Constants.*;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/searchstudent")
public class SearchStudent extends AjaxController {

    @Override
    public void process(
            HttpServletRequest req,
            HttpServletResponse resp,
            Session session,
            HttpSession httpSession,
            PrintWriter out) throws Exception {

        resp.setContentType("application/json");

        long classroomId = Long.parseLong(req.getParameter("classroom"));
        String subjectId = req.getParameter("subject");
        String filter = req.getParameter("filter");

        Gson gson = new Gson();

        JsonArray jsonStudents = new JsonArray();

        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);

        Criteria studentCriteria = session.createCriteria(Student.class)
                .add(Restrictions.eq("classRoom", classRoom))
                .add(Restrictions.eq("unaccounted", false));

        if (!filter.equals("all")) {
            studentCriteria = studentCriteria.add(Restrictions.eq("verified", Boolean.parseBoolean(filter)));
        }

        if (!subjectId.equals("all")) {
            Subject subject = (Subject) session.get(Subject.class, Long.parseLong(subjectId));
            Subject[] subjects = {subject};
            studentCriteria = studentCriteria.add(Restrictions.eq("subjects", subjects));
        }

        studentCriteria.list()
                .forEach(student -> add((Student) student, jsonStudents));

        out.print(gson.toJson(jsonStudents));

    }

    private void add(Student student, JsonArray jsonStudents) {

        JsonObject studentJson = new JsonObject();

        studentJson.addProperty(ID, student.getId().toString());
        studentJson.addProperty(NAME, student.toString());
        studentJson.addProperty(EMAIL, student.getUser().getEmail());
        studentJson.addProperty(NUMBER, student.getUser().getNumber());
        studentJson.addProperty(
                CLASSROOM,
                student.getClassRoom().getName() + " " + student.getClassRoom().getDivision()
        );
        studentJson.addProperty(ROLLNUMBER, student.getRollNumber());
        studentJson.addProperty(VERIFIED, student.isVerified());
        studentJson.add(SUBJECTS, addSubjects(student));

        jsonStudents.add(studentJson);
    }

    private JsonElement addSubjects(Student theStudent) {
        JsonArray jsonSubjects = new JsonArray();

        theStudent.getSubjects()
                .forEach(subject -> jsonSubjects.add(subject.getSubject().getName()));

        return jsonSubjects;
    }

}
