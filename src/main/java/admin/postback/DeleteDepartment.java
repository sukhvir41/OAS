/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.Department;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.PostBackController;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/departments/deletedepartment")
public class DeleteDepartment extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        Long departmentId = Long.parseLong(req.getParameter("departmentId"));
        session.delete(session.get(Department.class, departmentId));

        resp.sendRedirect("/OAS/admin/departments");

    }

}
