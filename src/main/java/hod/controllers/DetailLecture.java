/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hod.controllers;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Department;
import entities.Lecture;
import entities.Student;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/hod/lectures/detaillecture")
public class DetailLecture extends Controller {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());
        
        String lectureId = req.getParameter("lectureId");
        Lecture lecture = (Lecture) session.get(Lecture.class, lectureId);
        
        if (lecture.getTeaching().getClassRoom().getCourse().getDepartment().getId() == department.getId()) {
            List<Student> students = lecture.getTeaching()// this will contain all studentts first and then only absent students
                    .getClassRoom()
                    .getStudents()
                    .stream()
                    .filter(student -> student.getSubjects().contains(lecture.getTeaching().getSubject()))
                    .sorted()
                    .collect(Collectors.toList());
            
            req.setAttribute("total", students.size());// setting the total size first as the list will have only absent students later

            //todo: rewrite sql for this
            /*List<Student> present = lecture.getAttendance()
                    .stream()
                    .filter(attendance -> attendance.isAttended())
                    .map(attendance -> attendance.getStudent())
                    .collect(Collectors.toList());
            students.removeAll(present);//removing all present students from the students list

            Collections.sort(present);// sorting all students accordig to their to roll number*/

            //todo: rewrite sql for this
            //req.setAttribute("present", present);
            req.setAttribute("absent", students);
            //todo: rewrite sql for this
            //req.setAttribute("headcount", present.size());
            req.setAttribute("lecture", lecture);
            req.getRequestDispatcher("/WEB-INF/admin/detaillecture.jsp").include(req, resp);
        } else {
            resp.sendRedirect("/OAS/error");
        }
    }
    
}
