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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.AjaxController;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/ajax/getclass")
public class RegisterClass extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        String courseId = req.getParameter("course");
        resp.setContentType("text/json");
        if (courseId != null && !courseId.equals("")) {
            int courseid = Integer.parseInt(courseId);

            Course course = (Course) session.get(Course.class, courseid);
            List<ClassRoom> classes = course.getClassRooms();
            Gson gson = new Gson();
            JsonArray jsonClasses = new JsonArray();
            classes.stream()
                    .forEach(e -> add(e, jsonClasses));
            out.print(gson.toJson(jsonClasses));

        }
    }

    private void add(ClassRoom e, JsonArray jsonClasses) {
        JsonObject o = new JsonObject();
        o.addProperty("id", e.getId());
        o.addProperty("name", e.getName());
        jsonClasses.add(o);
    }

}
