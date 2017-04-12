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
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/teachers/searchteacherbyname")
public class SearchTeacherByName extends HttpServlet {

    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name;
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/json");
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            name = req.getParameter("name");

            List<Teacher> teachers = session.createCriteria(Teacher.class)
                    .add(Restrictions.or(Restrictions.like("fName", "%" + name + "%"), Restrictions.like("lName", "%" + name + "%")))
                    .list();
            Gson gson = new Gson();
            JsonArray jsonTeachers = new JsonArray();
            teachers.stream()
                    .forEach(teacher -> add(teacher,jsonTeachers));
            session.getTransaction().commit();
            session.close();
            out.print(gson.toJson(jsonTeachers));
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            session.close();
            out.print("error");
        } finally {
            out.close();
        }
    }

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("error");
        resp.getWriter().close();
        //doPost(req, resp);
    }

}
