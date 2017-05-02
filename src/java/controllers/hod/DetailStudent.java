/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.hod;

import entities.Department;
import entities.Login;
import entities.Student;
import entities.UserType;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/hod/students/detailstudent")
public class DetailStudent extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int studentId = Integer.parseInt(req.getParameter("studentId"));
        Student student = (Student) session.get(Student.class, studentId);

        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());

        if (student.getClassRoom().getCourse().getDepartment().getId() == department.getId()) {
            Login login = (Login) session.createCriteria(Student.class)
                    .add(Restrictions.eq("type", UserType.Student.toString()))
                    .add(Restrictions.eq("id", student.getId()))
                    .list()
                    .get(0);

            req.setAttribute("username", login.getUsername());
            req.setAttribute("student", student);
            req.getRequestDispatcher("/WEB-INF/hod/detailstudent.jsp").include(req, resp);
        } else {
            resp.sendRedirect("/OAS/error");
        }

    }

}
