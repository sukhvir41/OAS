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
@WebServlet(urlPatterns = "/getclasssubject")
public class ClassSubject extends HttpServlet {
    
    JsonArray jsonSubjects;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int classRoomId = Integer.parseInt(req.getParameter("classroomId"));
        PrintWriter out = resp.getWriter();
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classRoomId);
            List<Subject> subjects = classRoom.getSubjects();
            jsonSubjects = new JsonArray();
            subjects.stream()
                    .forEach(e -> addsubject(e));
            session.getTransaction().commit();
            session.close();
            Gson gson = new Gson();
            out.print(gson.toJson(jsonSubjects));
        } catch (Exception r) {
            out.print("error");
            session.getTransaction().rollback();
            session.close();
        } finally {
            out.close();
        }
        
    }
    
    private void addsubject(Subject e) {
        JsonObject o = new JsonObject();
        o.addProperty("id", e.getId());
        o.addProperty("name", e.getName());
        jsonSubjects.add(o);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("error");
        out.close();
    }
    
}
