/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classteacher.controllers;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Student;
import entities.Teacher;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/classteacher/students/activateteacher")
public class ActivateStudent extends Controller {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());
        
        int studentId = Integer.parseInt(req.getParameter("studentId"));
        
        Student student = (Student) session.get(Student.class, studentId);
        
        if (teacher.getClassRoom().getStudents().contains(student)) {
            student.setVerified(true);
            resp.sendRedirect("/OAS/teacher/classteacher/students/detailstudent?studentId=" + studentId);
        } else {
            resp.sendRedirect("/OAS/error");
        }
        
    }
    
}
