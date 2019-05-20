/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Lecture;
import entities.Student;
import org.hibernate.Session;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/lectures/detaillecture")
public class DetailLecture extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        String lectureId = req.getParameter("lectureId");
        Lecture lecture = (Lecture) session.get(Lecture.class, lectureId);

        List<Student> students = lecture.getTeaching()// this will contain all studentts first and then only absent students
                .getClassRoom()
                .getStudents()
                .stream()
                .filter(student -> student.getSubjects().contains(lecture.getTeaching().getSubject()))
                .collect(Collectors.toList());

        Collections.sort(students);// sorting all studnets according to their roll number

        req.setAttribute("total", students.size());// setting the total size first as the list will have only absent students later

        // todo: write sql for this
       /* List<Student> present = lecture.getAttendance()
                .stream()
                .filter(attendance -> attendance.isAttended())
                .map(attendance -> attendance.getStudent())
                .collect(Collectors.toList());
        students.removeAll(present);//removing all present students from the students list

        Collections.sort(present);// sorting all students accordig to their to roll number
*/
        // todo: write sql for this
        //req.setAttribute("present", present);
        req.setAttribute("absent", students);
        // todo: write sql for this
        //req.setAttribute("headcount", present.size());
        req.setAttribute("lecture", lecture);
        req.getRequestDispatcher("/WEB-INF/admin/detaillecture.jsp").include(req, resp);
    }

}
