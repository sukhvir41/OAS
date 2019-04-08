/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Department;
import entities.Teacher;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/departments/updatedepartment")
public class UpdateDepartment extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

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
                if (tempTeacher.getHodOf().isEmpty()) { // check if the teacher is hod of asome deaprtment or not if not remove as hod
                    tempTeacher.setHod(false);
                }
            }
        }
        department.setName(name);

        String from = req.getParameter("from");
        if (!from.equals("")) {
            resp.sendRedirect("/OAS/admin/departments#" + departmentId);
        } else {
            resp.sendRedirect("/OAS/admin/departments/detaildepartment?departmentId=" + departmentId);
        }

    }

}
