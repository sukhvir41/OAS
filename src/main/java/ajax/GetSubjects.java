/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import com.google.gson.Gson;
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
// do it using hibernate then native sql
        long classroomId = Long.parseLong(classroomString);


        //ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classId);
        //JsonArray jsonSubjects = new JsonArray();
       /* Set<Subject> subjects = classRoom.getSubjects();
        subjects.forEach(e -> add(e, jsonSubjects));

        Gson gson = new Gson();
        JsonObject subject = new JsonObject();
        subject.add(SUBJECTS, jsonSubjects);
        subject.addProperty(MINSUBJECTS, classRoom.getMinimumSubjects());
        out.print(gson.toJson(subject));*/

    }
}
