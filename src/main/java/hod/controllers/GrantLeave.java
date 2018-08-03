/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hod.controllers;

import entities.Department;
import entities.Student;
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
@WebServlet("/teacher/hod/students/grantleave")
public class GrantLeave extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());

        int studentId = Integer.parseInt(req.getParameter("studentId"));
        Student student = (Student) session.get(Student.class, studentId);
        
        if(student.getClassRoom().getCourse().getDepartment().getId() == department.getId()){
            req.setAttribute("student", student);
            req.getRequestDispatcher("/WEB-INF/hod/hodgrantleave.jsp").include(req, resp);
            
        }else{
            resp.sendRedirect("/OAS/error");
        }
    }

}
