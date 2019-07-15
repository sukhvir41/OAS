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
@WebServlet(urlPatterns = "/ajax/getsubjectsofclass")
public class GetSubjectOfClassRoom extends AjaxController {

    private final String ID = "id";
    private final String NAME = "name";

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        resp.setContentType("text/json");

        long classroomId = Long.parseLong(req.getParameter("classroom"));
        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
        /*Set<Subject> subjects = classRoom.getSubjects();
        JsonArray jsonSubjects = new JsonArray();
        Gson gson = new Gson();
        subjects.stream()
                .forEach(e -> add(e, jsonSubjects));*/
        //out.print(gson.toJson(jsonSubjects));

    }

    private void add(Subject e, JsonArray jsonSubjects) {
        JsonObject subejct = new JsonObject();
        subejct.addProperty(ID, e.getId());
        subejct.addProperty(NAME, e.getName());
        jsonSubjects.add(subejct);
    }
}
