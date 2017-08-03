/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.Course;
import entities.Department;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/courses/addcourse")
public class AddCourse extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        int departmentId = Integer.parseInt(req.getParameter("departmentId"));
        String name = req.getParameter("coursename");

        Department department = (Department) session.get(Department.class, departmentId);
        Course course = new Course(name);
        department.addCourse(course);
        session.save(course);

        if (req.getParameter("from") != null) {
            resp.sendRedirect("/OAS/admin/departments/detaildepartment?departmentId=" + departmentId);

        } else {
            resp.sendRedirect("/OAS/admin/courses");
        }

    }

}
