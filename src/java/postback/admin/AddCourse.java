/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.Course;
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
@WebServlet(urlPatterns = "/admin/courses/addcourse")
public class AddCourse extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            int departmentId = Integer.parseInt(req.getParameter("departmentId"));
            String name = req.getParameter("coursename");
            
            Department department = (Department) session.get(Department.class, departmentId);
            Course course = new Course(name);
            department.addCourse(course);
            session.save(course);                
            session.getTransaction().commit();
            session.close();
            if (req.getParameter("from") != null) {
                resp.sendRedirect("/OAS/admin/departments/detaildepartment?departmentId=" + departmentId);
                
            } else {
                resp.sendRedirect("/OAS/admin/courses");
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            
            resp.sendRedirect("/OAS/error");
        }
        
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/admin");
    }
    
}
