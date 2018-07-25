/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entities.Department;
import entities.Teacher;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;
import static utility.Constants.*;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/teachers/searchteacher")
public class SearchTeacher extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        resp.setContentType("text/json");
        String departmentId;
        String verified;

        List<Teacher> teachers;

        departmentId = req.getParameter("departmentId");
        verified = req.getParameter("verified");
        if (departmentId.equals("all")) {

            if (verified.equals("all")) {
                teachers = session.createCriteria(Teacher.class)
                        .list();
            } else {
                teachers = session.createCriteria(Teacher.class)
                        .add(Restrictions.eq("verified", Boolean.parseBoolean(verified)))
                        .list();

            }
        } else {
            Department department = (Department) session.get(Department.class, Integer.parseInt(departmentId));

            if (verified.equals("all")) {
                teachers = department.getTeachers();

            } else {

                teachers = session.createCriteria(Teacher.class)
                        .add(Restrictions.eq("department", department))
                        .add(Restrictions.eq("verified", Boolean.parseBoolean(verified)))
                        .list();

            }
        }

        Gson gson = new Gson();
        JsonArray jsonTeachers = new JsonArray();
        teachers.stream()
                .forEach(teacher -> add(teacher, jsonTeachers));
        out.print(gson.toJson(jsonTeachers));

    }

    private void add(Teacher teacher, JsonArray jsonTeachers) {
        JsonObject teacherJson = new JsonObject();

        teacherJson.addProperty(ID, teacher.getId());
        teacherJson.addProperty(NAME, teacher.toString());
        teacherJson.addProperty(NUMBER, teacher.getNumber());
        teacherJson.addProperty(EMAIL, teacher.getEmail());
        teacherJson.addProperty(HOD, teacher.isHod());
        if (teacher.isHod()) {
            teacherJson.add(HODOF, addDepartment(teacher.getHodOf()));
        } else {
            teacherJson.addProperty(HODOF, "not hod");
        }
        teacherJson.addProperty(CLASSTEACHER, teacher.getClassRoom() == null ? "" : teacher.getClassRoom().getName());
        teacherJson.add(DEPARTMENTS, addDepartment(teacher.getDepartment()));
        teacherJson.addProperty(VERIFIED, teacher.isVerified());

        jsonTeachers.add(teacherJson);

    }

    private JsonElement addDepartment(List<Department> departments) {
        JsonArray department = new JsonArray();

        departments.stream()
                .forEach(e -> department.add(e.getName()));

        return department;
    }

}