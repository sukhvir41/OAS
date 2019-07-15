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
import org.hibernate.Session;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Set;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/getclasssubject")
public class ClassSubject extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int classRoomId = Integer.parseInt(req.getParameter("classroomId"));

        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classRoomId);
       // Set<Subject> subjects = classRoom.getSubjects();
        JsonArray jsonSubjects = new JsonArray();
        /*subjects.stream()
                .forEach(e -> addsubject(e, jsonSubjects));*/
        session.getTransaction().commit();
        session.close();
        Gson gson = new Gson();
        out.print(gson.toJson(jsonSubjects));

    }

    private void addsubject(Subject e, JsonArray jsonSubjects) {
        JsonObject o = new JsonObject();
        o.addProperty("id", e.getId());
        o.addProperty("name", e.getName());
        jsonSubjects.add(o);
    }

}
