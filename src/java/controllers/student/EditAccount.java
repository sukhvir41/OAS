/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.student;

import entities.Course;
import entities.Login;
import entities.Student;
import entities.UserType;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(urlPatterns = "/account/editstudent")
public class EditAccount extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Student student = (Student) req.getSession().getAttribute("student");
        student = (Student) session.get(Student.class, student.getId());

        Login login = (Login) session.createCriteria(Login.class)
                .add(Restrictions.eq("id", student.getId()))
                .add(Restrictions.eq("type", UserType.Student.toString()))
                .list()
                .get(0);

        List<Course> courses = session.createCriteria(Course.class)
                .list();

        req.setAttribute("username", login.getUsername());
        req.setAttribute("student", student);
        req.setAttribute("courses", courses);
        req.getRequestDispatcher("/WEB-INF/student/studenteditaccount.jsp").include(req, resp);
    }

}
