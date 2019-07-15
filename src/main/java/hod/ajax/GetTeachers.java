/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hod.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entities.Department;
import entities.Teacher;
import org.hibernate.Session;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/hod/ajax/getteachers")
public class GetTeachers extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());

        Gson gson = new Gson();
        JsonArray jsonTeachers = new JsonArray();

        department.getTeachers()
                .stream()
                .forEach(teacher -> add(teacher.getTeacher(), jsonTeachers));

        out.print(gson.toJson(jsonTeachers));

    }

    private void add(Teacher teacher, JsonArray jsonTeachers) {
        JsonObject teacherJson = new JsonObject();
        teacherJson.addProperty("id", teacher.getId().toString());
        teacherJson.addProperty("name", teacher.toString());
        teacherJson.addProperty("number", teacher.getUser().getNumber());
        teacherJson.addProperty("email", teacher.getUser().getEmail());
        teacherJson.addProperty("hod", teacher.isHod());
        if (teacher.isHod()) {
            teacherJson.add("hodof", addDepartment(teacher.getHodOf()));
        } else {
            teacherJson.addProperty("hodof", "not hod");
        }
        teacherJson.addProperty("classteacher", teacher.getClassRoom() == null ? "" : teacher.getClassRoom().getName());
        //teacherJson.add("departments", addDepartment(teacher.getDepartment()));
        teacherJson.addProperty("verified", teacher.isVerified());
        jsonTeachers.add(teacherJson);

    }

    private JsonElement addDepartment(Collection<Department> departments) {
        JsonArray department = new JsonArray();
        departments.stream()
                .forEach(e -> {
                    department.add(e.getName());
                });
        return department;
    }
}
