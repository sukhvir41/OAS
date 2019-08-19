/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.hibernate.Session;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/ajax/get-all-courses")
public class GetAllCourses extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        Gson gson = new Gson();

        var nativeQuery = session.createNativeQuery("" +
                "select" +
                "   coalesce(cast(array_to_json(array_agg(row_to_json(c))) as varchar), '[]')" +
                "from(" +
                "   select" +
                "       id," +
                "       name" +
                "   from" +
                "   course " +
                ") c");

        var courses = (String) nativeQuery.getSingleResult();

        var coursesJson = gson.fromJson(courses, JsonArray.class);

        var jsonObject = getSuccessJson();

        jsonObject.add(DATA, coursesJson);

        out.println(gson.toJson(jsonObject));
    }
}
