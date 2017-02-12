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
import entities.Student;
import entities.Teacher;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/students/searchstudentbyname")
public class SearchStudentByName extends HttpServlet {
    
    JsonArray jsonStudents;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name;
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/json");
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            name = req.getParameter("name");
            List<Student> students = session.createCriteria(Student.class)
                    .add(Restrictions.or(Restrictions.like("fName", "%" + name + "%"), Restrictions.like("lName", "%" + name + "%")))
                    .list();
            jsonStudents = new JsonArray();
            Gson gson = new Gson();
            students.stream()
                    .forEach(e -> add(e));
            out.print(gson.toJson(jsonStudents));
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            out.print("error");
            e.printStackTrace();
        } finally {
            out.close();
        }
        
    }
    
    private void add(Student e) {
        JsonObject student = new JsonObject();
        student.addProperty("id", e.getId());
        student.addProperty("name", e.getfName() + " " + e.getlName());
        student.addProperty("email", e.getEmail());
        student.addProperty("number", e.getNumber());
        student.addProperty("classroom", e.getClassRoom().getName() + " " + e.getClassRoom().getDivision());
        student.addProperty("rollnumber", e.getRollNumber());
        student.addProperty("verified", e.isVerified());
        student.add("subjects", addSubjects(e));
        jsonStudents.add(student);
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
