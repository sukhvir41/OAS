/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.Course;
import org.hibernate.Session;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/ajax/getcourse")
public class RegisterCourse extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        resp.setContentType("text/json");

        List<Course> courses = (List) session.createCriteria(Course.class)
                .list();

        Gson gson = new Gson();
        JsonArray jsonCourses = new JsonArray();
        courses.stream()
                .forEach(e -> add(e, jsonCourses));

        out.print(gson.toJson(jsonCourses));

    }

    public void add(Course theCourse, JsonArray jsonCourses) {
        JsonObject course = new JsonObject();
        course.addProperty("id", theCourse.getId());
        course.addProperty("name", theCourse.getName());
        jsonCourses.add(course);
    }
}
