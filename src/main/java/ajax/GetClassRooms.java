/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.hibernate.Session;
import org.jooq.tools.StringUtils;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/ajax/get-classrooms")
public class GetClassRooms extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var gson = new Gson();

        var courseIdString = req.getParameter("course");

        if (StringUtils.isBlank(courseIdString)) {
            onError(req, resp);
            return;
        }

        long courseId = Long.parseLong(courseIdString);

        var nativeQuery = session.createNativeQuery("" +
                "select" +
                "   coalesce(cast(array_to_json(array_agg(row_to_json(c))) as varchar),'[]')" +
                "from(" +
                "   select" +
                "       id," +
                "       name," +
                "       minimum_subjects" +
                "   from" +
                "   class_room" +
                "   where course_fid = :courseId" +
                ") c"
        );

        nativeQuery.setParameter("courseId", courseId);

        var classrooms = (String) nativeQuery.getSingleResult();

        var jsonObject = getSuccessJson();

        var classroomJson = gson.fromJson(classrooms, JsonArray.class);

        jsonObject.add(DATA, classroomJson);

        out.println(gson.toJson(jsonObject));

    }

}
