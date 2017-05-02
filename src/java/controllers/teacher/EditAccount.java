/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.teacher;

import entities.Department;
import entities.Login;
import entities.Teacher;
import entities.UserType;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
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
@WebServlet("/account/editteacher")
public class EditAccount extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Teacher sessionTeacher = (Teacher) httpSession.getAttribute("teacher");
        Teacher teacher = (Teacher) session.get(Teacher.class, sessionTeacher.getId());

        Login login = (Login) session.createCriteria(Login.class)
                .add(Restrictions.eq("id", teacher.getId()))
                .add(Restrictions.eq("type", UserType.Student.toString()))
                .list()
                .get(0);

        List<Department> departments = session.createCriteria(Department.class)
                .list();

        departments = departments.stream()
                .filter(department -> !teacher.getDepartment().contains(department))
                .collect(Collectors.toList());

        req.setAttribute("username", login.getUsername());
        req.setAttribute("teacher", teacher);
        req.setAttribute("departments", departments);
        req.getRequestDispatcher("/WEB-INF/teacher/teachereditprofile.jsp").include(req, resp);

    }

}
