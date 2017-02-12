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
@WebServlet(urlPatterns = "/ajax/getsubjectsofclass")
public class GetSubjectOfClassRoom extends HttpServlet {

    JsonArray jsonSubjects;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/json");
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            int classroomId = Integer.parseInt(req.getParameter("classroom"));
            ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
            List<Subject> subjects = classRoom.getSubjects();
            jsonSubjects = new JsonArray();
            Gson gson = new Gson();
            subjects.stream()
                    .forEach(e -> add(e));
            session.getTransaction().commit();
            session.close();
            out.print(gson.toJson(jsonSubjects));
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            out.print("error");
        } finally {
            out.close();
        }

    }

    private void add(Subject e) {
        JsonObject subejct = new JsonObject();
        subejct.addProperty("id", e.getId());
        subejct.addProperty("name", e.getName());
        jsonSubjects.add(subejct);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("error");
        out.close();
    }

}
