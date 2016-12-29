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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/ajax/getcourse")
public class RegisterCourse extends HttpServlet {

    static JsonArray jsonCourses;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter out = resp.getWriter();
        Session session = Utils.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Course");
        List<Course> courses = (List<Course>) query.list();
        session.getTransaction().commit();
        session.close();
        Gson gson = new Gson();
        jsonCourses = new JsonArray();
        courses.stream()
                .forEach(e -> add(e));
        System.out.println(gson.toJson(jsonCourses));
        out.print(gson.toJson(jsonCourses));
        out.close();
    }

    public void add(Course e) {
        JsonObject o = new JsonObject();
        o.addProperty("id", e.getId());
        o.addProperty("name", e.getName());
        jsonCourses.add(o);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        out.print("error");
        out.close();
    }

}
