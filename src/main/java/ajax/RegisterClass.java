/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.ClassRoom;
import entities.Course;
import utility.AjaxController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/ajax/getclass")
public class RegisterClass extends AjaxController {

    private final String ID = "id";
    private final String NAME = "name";

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        resp.setContentType("text/json");

        long courseid = Long.parseLong(req.getParameter("course"));

        Course course = (Course) session.get(Course.class, courseid);
        List<ClassRoom> classes = course.getClassRooms();
        Gson gson = new Gson();
        JsonArray jsonClasses = new JsonArray();
        classes.stream()
                .forEach(e -> add(e, jsonClasses));
        out.print(gson.toJson(jsonClasses));

    }

    private void add(ClassRoom theClassRoom, JsonArray jsonClasses) {
        JsonObject classRoom = new JsonObject();
        classRoom.addProperty(ID, theClassRoom.getId());
        classRoom.addProperty(NAME, theClassRoom.getName());
        jsonClasses.add(classRoom);
    }

}
