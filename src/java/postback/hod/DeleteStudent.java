/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.hod;

import entities.Department;
import entities.Student;
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
@WebServlet(urlPatterns = "/teacher/hod/students/deletestudent")
public class DeleteStudent extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int studentId = Integer.parseInt(req.getParameter("studentId"));
        Student student = (Student) session.get(Student.class, studentId);
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());

        if (student.getClassRoom().getCourse().getDepartment().getId() == department.getId()) {
            if (student.isUnaccounted()) {
                session.delete(student);
                resp.sendRedirect("/OAS/teacher/hod/students");
            } else {
                resp.sendRedirect("/OAS/teacher/hod/students/detailstudent?studentId=" + studentId);
            }
        }else{
            resp.sendRedirect("/OAS/error");
        }
    }

}
