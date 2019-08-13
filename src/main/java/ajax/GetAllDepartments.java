/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.hibernate.Session;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author sukhvir
 * <p>
 * returns all the department
 */
@WebServlet(urlPatterns = "/ajax/get-all-departments")
public class GetAllDepartments extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var gson = new Gson();

        var query = session.createNativeQuery("" +
                "select " +
                "   cast(array_to_json(array_agg(row_to_json(d))) as varchar)" +
                "from(" +
                "   select" +
                "       \"id\"," +
                "       \"name\"" +
                "   from" +
                "   department" +
                ") d");

        var departments = (String) query.getSingleResult();

        var jsonObject = getSuccessJson();

        var departmentsJson = gson.fromJson(departments, JsonArray.class); // converting json string array to jsonArray

        jsonObject.add(DATA, departmentsJson);

        out.println(gson.toJson(jsonObject));
    }
}
