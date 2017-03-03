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
import java.util.ArrayList;
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
@WebServlet(urlPatterns = "/admin/ajax/teachers/searchteacher")
public class SearchTeacher extends HttpServlet {

    JsonArray jsonTeachers;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/json");
        String departmentId;
        String verified;
        Session session = Utils.openSession();
        session.beginTransaction();
        List<Teacher> teachers;
        try {
            departmentId = req.getParameter("departmentId");
            verified = req.getParameter("verified");
            if (departmentId.equals("all")) {
                if (verified.equals("all")) {
                    teachers = session.createCriteria(Teacher.class).list();
                } else {
                    if (Boolean.parseBoolean(verified)) {
                        teachers = session.createCriteria(Teacher.class)
                                .add(Restrictions.eq("verified", true))
                                .list();
                    } else {
                        teachers = session.createCriteria(Teacher.class)
                                .add(Restrictions.eq("verified", false))
                                .list();
                    }
                }
            } else {
                if (verified.equals("all")) {
                    teachers = ((Department) session.get(Department.class, Integer.parseInt(departmentId))).getTeachers();
                } else {
                    teachers = ((Department) session.get(Department.class, Integer.parseInt(departmentId))).getTeachers();
                    List<Teacher> temp = new ArrayList<>();
                    if (Boolean.parseBoolean(verified)) {
                        teachers.stream()
                                .filter(e -> e.isVerified() == true)
                                .forEach(temp::add);
                        teachers = temp;
                    } else {
                        teachers.stream()
                                .filter(e -> e.isVerified() == false)
                                .forEach(temp::add);
                        teachers = temp;
                    }
                }
            }

            Gson gson = new Gson();
            jsonTeachers = new JsonArray();
            teachers.stream()
                    .forEach(e -> add(e));
            out.print(gson.toJson(jsonTeachers));

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            session.close();
            out.print("error");
        } finally {

            out.close();
        }
    }

    private void add(Teacher e) {
        JsonObject teacher = new JsonObject();
        teacher.addProperty("id", e.getId());
        teacher.addProperty("name", e.getfName() + " " + e.getlName());
        teacher.addProperty("number", e.getNumber());
        teacher.addProperty("email", e.getEmail());
        teacher.addProperty("hod", e.isHod());
        if (e.isHod()) {
            teacher.add("hodof", addDepartment(e.getHodOf()));
        } else {
            teacher.addProperty("hodof", "not hod");
        }
        teacher.addProperty("classteacher", e.getClassRoom() == null ? "" : e.getClassRoom().getName());
        teacher.add("departments", addDepartment(e.getDepartment()));
        teacher.addProperty("verified", e.isVerified());
        jsonTeachers.add(teacher);

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
