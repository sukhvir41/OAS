/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Department;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/departments/editdepartment")
public class EditDepartment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int departmentId = 1;
        try {
            departmentId = Integer.parseInt(req.getParameter("departmentId"));
        
        Session session = Utils.openSession();
        session.beginTransaction();
        Department department = (Department) session.get(Department.class, departmentId);
        req.setAttribute("department", department);
        req.getRequestDispatcher("/WEB-INF/admin/editdepartment.jsp").forward(req, resp);
        session.getTransaction().commit();
        session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
