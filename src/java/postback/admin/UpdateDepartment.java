/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

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
@WebServlet(urlPatterns = "/admin/departments/updatedepartment")
public class UpdateDepartment extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            int departmentId = Integer.parseInt(req.getParameter("departmentId"));
            String name = req.getParameter("departmentname");
            Department department = (Department) session.get(Department.class, departmentId);
            department.setName(name);
            session.getTransaction().commit();
            session.close();
            String from = req.getParameter("from");
            if (!from.equals("")) {
                resp.sendRedirect("/OAS/admin/departments#" + departmentId);
            } else {
                resp.sendRedirect("/OAS/admin/departments/detaildepartment?departmentId=" + departmentId);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            
            resp.sendRedirect("/OAS/error");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/admin/departments");
    }
    
}
