/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Attendance;
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
//@WebServlet(urlPatterns = "/admin/students/unaccountstudent")
public class UnaccountStudent extends Controller {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int studentId = Integer.parseInt(req.getParameter("studentId"));
        Student student = (Student) session.get(Student.class, studentId);
        
        if (student.isVerified()) {
            resp.sendRedirect("/OAS/admin/students/detailstudent?studentId=" + studentId);
        } else {
            student.setUnaccounted(true);
            
            student.getSubjects()
                    .stream()
                    .forEach(subject -> subject.getStudents().remove(student));
            student.getSubjects().clear();
            
            student.getAttendance()
                    .stream()
                    .forEach(attendance -> removeAttendance(attendance, session));
            student.getAttendance().clear();
            
            student.getClassRoom().getStudents().remove(student);
            
            student.setClassRoom(null);
            
            resp.sendRedirect("/OAS/admin/students");
        }
    }
    
    private void removeAttendance(Attendance attendance, Session session) {
        //attendance.setStudent(null);
        //attendance.setLecture(null);
        session.delete(attendance);
    }
    
}
