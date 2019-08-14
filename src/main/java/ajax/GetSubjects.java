/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.commons.lang3.StringUtils;
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
@WebServlet(urlPatterns = "/ajax/get-subjects")
public class GetSubjects extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var classroomString = req.getParameter("classroom");

        if (StringUtils.isBlank(classroomString)) {
            onError(req, resp);
            return;
        }

        var gson = new Gson();
        long classroomId = Long.parseLong(classroomString);

        var nativeQuery = session.createNativeQuery("" +
                "select" +
                "   coalesce(cast(array_to_json(array_agg(row_to_json(sub))) as varchar),'[]')" +
                "from(" +
                "   select" +
                "       sub.id," +
                "       sub.\"name\"," +
                "       sub.elective" +
                "   from" +
                "       subject sub" +
                "   inner join subject_class_link scl " +
                "   on sub.id = scl.subject_fid" +
                "   inner join class_room cr " +
                "   on cr.id = scl.class_room_fid" +
                "   where" +
                "   cr.id = :classroomId" +
                ") as sub;")
                .setParameter("classroomId", classroomId);

        var subjects = (String) nativeQuery.getSingleResult();

        var jsonObject = getSuccessJson();

        var subjectsJson = gson.fromJson(subjects, JsonArray.class);

        jsonObject.add(DATA, subjectsJson);

        out.println(gson.toJson(jsonObject));
    }
}
