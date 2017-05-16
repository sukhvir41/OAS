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
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(urlPatterns = "/ajax/getsubjectsofclass")
public class GetSubjectOfClassRoom extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        resp.setContentType("text/json");

        int classroomId = Integer.parseInt(req.getParameter("classroom"));
        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
        List<Subject> subjects = classRoom.getSubjects();
        JsonArray jsonSubjects = new JsonArray();
        Gson gson = new Gson();
        subjects.stream()
                .forEach(e -> add(e, jsonSubjects));
        out.print(gson.toJson(jsonSubjects));

    }

    private void add(Subject e, JsonArray jsonSubjects) {
        JsonObject subejct = new JsonObject();
        subejct.addProperty("id", e.getId());
        subejct.addProperty("name", e.getName());
        jsonSubjects.add(subejct);
    }
}
