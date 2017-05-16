/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.admin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entities.Department;
import entities.Teacher;
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
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/teachers/searchteacherbyname")
public class SearchTeacherByName extends AjaxController {

    private void add(Teacher teacher, JsonArray jsonTeachers) {
        JsonObject teacherJson = new JsonObject();
        teacherJson.addProperty("id", teacher.getId());
        teacherJson.addProperty("name", teacher.toString());
        teacherJson.addProperty("number", teacher.getNumber());
        teacherJson.addProperty("email", teacher.getEmail());
        teacherJson.addProperty("hod", teacher.isHod());
        if (teacher.isHod()) {
            teacherJson.add("hodof", addDepartment(teacher.getHodOf()));
        } else {
            teacherJson.addProperty("hodof", "not hod");
        }
        teacherJson.addProperty("classteacher", teacher.getClassRoom() == null ? "" : teacher.getClassRoom().getName());
        teacherJson.add("departments", addDepartment(teacher.getDepartment()));
        teacherJson.addProperty("verified", teacher.isVerified());
        jsonTeachers.add(teacherJson);

    }

    private JsonElement addDepartment(List<Department> departments) {
        JsonArray department = new JsonArray();
        departments.stream()
                .forEach(e -> {
                    department.add(e.getName());
                });
        return department;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        String name;

        resp.setContentType("text/json");

        name = req.getParameter("name");

        List<Teacher> teachers = session.createCriteria(Teacher.class)
                .add(Restrictions.or(Restrictions.like("fName", "%" + name + "%"), Restrictions.like("lName", "%" + name + "%")))
                .list();
        Gson gson = new Gson();
        JsonArray jsonTeachers = new JsonArray();
        teachers.stream()
                .forEach(teacher -> add(teacher, jsonTeachers));

        out.print(gson.toJson(jsonTeachers));

    }

}
