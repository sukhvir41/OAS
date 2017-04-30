/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.hod;

import entities.Department;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/hod/students")
public class HodStudents extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());

        req.setAttribute("course", department.getCourses());
        req.getRequestDispatcher("/WEB-INF/hod/hodstudents.jsp").include(req, resp);
    }

}
