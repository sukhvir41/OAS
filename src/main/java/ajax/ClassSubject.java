/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entities.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/ajax/get-classroom-subjects")
public class ClassSubject extends AjaxController {


    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {


        var classRoomIdString = req.getParameter("classroomId");

        if (StringUtils.isBlank(classRoomIdString)) {
            onError(req, resp);
            return;
        }

        var classRoomId = Long.parseLong(classRoomIdString);

        var holder = CriteriaHolder.getQueryHolder(session, Subject.class, SubjectClassRoomLink.class);

        holder.getQuery().where(
                holder.getCriteriaBuilder()
                        .equal(holder.getRoot().get(SubjectClassRoomLink_.classRoom), classRoomId)
        );
        holder.getQuery().select(holder.getRoot().get(SubjectClassRoomLink_.subject));


        JsonArray subjectsJson = session.createQuery(holder.getQuery())
                .stream()
                .map(this::jsonSubject)
                .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);

        Gson gson = new Gson();
        var jsonObject = new JsonObject();
        jsonObject.addProperty(STATUS, true);
        jsonObject.add(DATA, subjectsJson);
        out.print(gson.toJson(jsonObject));

    }

    private JsonObject jsonSubject(Subject subject) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", subject.getId());
        jsonObject.addProperty("name", subject.getName());
        return jsonObject;
    }

}
