/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.controllers;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Student;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/student/lectures")
public class StudentLectures extends Controller {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Student student = (Student) req.getSession().getAttribute("student");
        student = (Student) session.get(Student.class, student.getId());
        
        req.setAttribute("subjects", student.getSubjects());
        req.getRequestDispatcher("/WEB-INF/student/studentlectures.jsp").include(req, resp);
        
    }
    
}
