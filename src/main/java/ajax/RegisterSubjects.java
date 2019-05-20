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
@WebServlet(urlPatterns = "/ajax/getsubjects")
public class RegisterSubjects extends AjaxController {

    private final String ID = "id";
    private final String NAME = "name";
    private final String ELECTIVE = "elective";
    private final String SUBJECTS = "subjects";
    private final String MINSUBJECTS = "minimumsubjects";

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        resp.setContentType("text/json");

        long classId = Long.parseLong(req.getParameter("class"));

        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classId);
        JsonArray jsonSubjects = new JsonArray();
        Set<Subject> subjects = classRoom.getSubjects();
        subjects.forEach(e -> add(e, jsonSubjects));

        Gson gson = new Gson();
        JsonObject subject = new JsonObject();
        subject.add(SUBJECTS, jsonSubjects);
        subject.addProperty(MINSUBJECTS, classRoom.getMinimumSubjects());
        out.print(gson.toJson(subject));

    }

    private void add(Subject e, JsonArray jsonSubjects) {
        JsonObject o = new JsonObject();
        o.addProperty(ID, e.getId());
        o.addProperty(NAME, e.getName());
        o.addProperty(ELECTIVE, e.isElective());
        jsonSubjects.add(o);
    }

}
