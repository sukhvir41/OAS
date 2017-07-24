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
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import utility.AjaxController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/ajax/getdepartment")
public class RegisterDepartment extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        resp.setContentType("text/json");

      
        Query query = session.createQuery("from Department");
        List<Department> deparments = (List<Department>) query.list();

        JsonArray jsonDepartments = new JsonArray();
        deparments.stream()
                .forEach(e -> add(e, jsonDepartments));

        Gson gson = new Gson();
        out.print(gson.toJson(jsonDepartments));

    }

    private void add(Department e, JsonArray jsonDepartments) {
        JsonObject o = new JsonObject();
        o.addProperty("id", e.getId());
        o.addProperty("name", e.getName());
        jsonDepartments.add(o);
    }

}
