/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.Department;
import org.hibernate.Session;
import utility.PostBackController;
import utility.UrlBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/departments/delete-department-post")
public class DeleteDepartment extends PostBackController {

    @Override
    public void process(
            HttpServletRequest req,
            HttpServletResponse resp,
            Session session,
            HttpSession httpSession,
            PrintWriter out) throws Exception {

        Long departmentId = Long.parseLong(req.getParameter("departmentId"));
        Department department = session.get(Department.class, departmentId);

        req.setAttribute("name", department.getName());

        UrlBuilder parameters = new UrlBuilder();

        session.delete(department);
        parameters.addSuccessParameter()
                .addMessage(department.getName() + " was deleted");
        resp.sendRedirect(parameters.getUrl("/OAS/admin/departments"));
    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = (String) req.getAttribute("name");

        resp.sendRedirect(
                new UrlBuilder()
                        .addErrorParameter()
                        .addMessage("unable to delete department " + name)
                        .getUrl("/OAS/admin/departments")
        );
    }
}
