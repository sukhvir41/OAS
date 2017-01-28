/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.ClassRoom;
import entities.Course;
import entities.Subject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(urlPatterns = "/ajax/getsubjects")
public class RegisterSubjects extends HttpServlet {

    JsonArray jsonSubjects;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        String courseId = req.getParameter("course");
        String classId = req.getParameter("class");
        if (courseId != null && !courseId.equals("") && classId != null && !classId.equals("")) {
            int courseid = Integer.parseInt(courseId);
            PrintWriter out = resp.getWriter();
            Session session = Utils.openSession();
            session.beginTransaction();
            Course course = (Course) session.get(Course.class, courseid);
            ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, Integer.parseInt(classId));
            jsonSubjects = new JsonArray();
            List<Subject> subjects = classRoom.getSubjects();
            subjects.stream()
                    .forEach(e -> add(e));
            session.getTransaction().commit();
            session.close();

            Gson gson = new Gson();
            JsonObject subject = new JsonObject();
            subject.add("subjects", jsonSubjects);
            subject.addProperty("minimumsubjects", classRoom.getMinimumSubjects());
            out.print(gson.toJson(subject));
            out.close();

        }

    }

    private void add(Subject e) {
        JsonObject o = new JsonObject();
        o.addProperty("id", e.getId());
        o.addProperty("name", e.getName());
        o.addProperty("elective", e.isElective());
        jsonSubjects.add(o);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        out.print("error");
        out.close();
    }

}
