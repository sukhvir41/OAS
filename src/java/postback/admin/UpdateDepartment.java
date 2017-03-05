/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.Department;
import entities.Teacher;
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
            String teacherId = req.getParameter("teacherId");
            String name = req.getParameter("departmentname");
            Department department = (Department) session.get(Department.class, departmentId);
            if (teacherId != null) {
                Teacher teacher = (Teacher) session.get(Teacher.class, Integer.parseInt(teacherId));
                if (department.getHod() == null) {
                    teacher.addHodOf(department);
                } else {
                    Teacher tempTeacher = department.getHod();
                    tempTeacher.getHodOf().remove(department);
                    department.setHod(null);
                    teacher.addHodOf(department);
                }
            }
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
