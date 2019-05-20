/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.Department;
import org.hibernate.Session;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/ajax/getdepartment")
public class RegisterDepartment extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        resp.setContentType("text/json");

        List<Department> deparments = (List) session.createCriteria(Department.class)
                .list();

        JsonArray jsonDepartments = new JsonArray();

        deparments.forEach(department -> add(department, jsonDepartments));

        Gson gson = new Gson();

        out.print(gson.toJson(jsonDepartments));

    }

    private void add(Department theDepartment, JsonArray jsonDepartments) {
        JsonObject department = new JsonObject();
        department.addProperty("id", theDepartment.getId());
        department.addProperty("name", theDepartment.getName());
        jsonDepartments.add(department);
    }

}
