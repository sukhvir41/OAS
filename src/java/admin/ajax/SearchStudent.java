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
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;
import static utility.Constants.*;

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
        
        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
        
        Criteria studentCriteria = session.createCriteria(Student.class)
                .add(Restrictions.eq("classRoom", classRoom))
                .add(Restrictions.eq("unaccounted", false));
        
        if (subjectId.equals("all")) {
//            ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);

            // have to check this has any performance issue
//            Criteria studentCriteria = session.createCriteria(Student.class)
//                    .add(Restrictions.eq("classRoom", classRoom))
//                    .add(Restrictions.eq("unaccounted", false));
            if (filter.equals("all")) {
//                classRoom.getStudents()
//                        .stream()
//                        .filter(student -> !student.isUnaccounted())
//                        .forEach(student -> add(student, jsonStudents));
                studentCriteria.list()
                        .forEach(student -> add((Student) student, jsonStudents));
                
            } else {
//                classRoom.getStudents()
//                        .stream()
//                        .filter(student -> !student.isUnaccounted())
//                        .filter(e -> e.isVerified() == Boolean.parseBoolean(filter))
//                        .forEach(e -> add(e, jsonStudents));

                studentCriteria.add(Restrictions.eq("verified", Boolean.parseBoolean(filter)))
                        .list()
                        .forEach(student -> add((Student) student, jsonStudents));
            }
        } else {
//            ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
            Subject subject = (Subject) session.get(Subject.class, Integer.parseInt(subjectId));
            
            Subject[] subjects = {subject};
            if (filter.equals("all")) {
//                classRoom.getStudents()
//                        .stream()
//                        .filter(student -> !student.isUnaccounted())
//                        .filter(student -> student.getSubjects().contains(subject))
//                        .forEach(student -> add(student, jsonStudents));
                studentCriteria.add(Restrictions.in("subjects", subjects))
                        .list()
                        .forEach(student -> add((Student) student, jsonStudents));
                
            } else {
                studentCriteria.add(Restrictions.in("subjects", subjects))
                        .add(Restrictions.eq("verified", Boolean.parseBoolean(filter)))
                        .list()
                        .forEach(student -> add((Student) student, jsonStudents));

//                classRoom.getStudents()
//                        .stream()
//                        .filter(student -> !student.isUnaccounted())
//                        .filter(student -> student.isVerified() == Boolean.parseBoolean(filter))
//                        .filter(student -> student.getSubjects().contains(subject))
//                        .forEach(e -> add(e, jsonStudents));
            }
            
        }
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
                .forEach(subject -> jsonSubjects.add(subject.getName()));
        
        return jsonSubjects;
    }
    
}
