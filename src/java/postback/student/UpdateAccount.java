/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.student;

import entities.ClassRoom;
import entities.Course;
import entities.Student;
import entities.Subject;
import entities.UserType;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Controller;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/account/updatestudent")
public class UpdateAccount extends PostBackController {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Student sessionStudent = (Student) req.getSession().getAttribute("student");
        Student student = (Student) session.get(Student.class, sessionStudent.getId());
        
        String email = req.getParameter("email");
        String number = req.getParameter("number");
        String fName = req.getParameter("fname");
        String lName = req.getParameter("lname");
        int rollnumber = Integer.parseInt(req.getParameter("rollnumber"));
        
        if (!student.getEmail().equals(email)) {
            
            student.setEmail(email);
            resp.sendRedirect("/OAS/logout");
        } else {
            resp.sendRedirect("/OAS/account/editstudent");
        }
        student.setVerified(false);
        student.setUnaccounted(false);
        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, Integer.parseInt(req.getParameter("classroom")));
        
        student.getClassRoom().getStudents().remove(student);
        student.addClassRoom(classRoom); // addding

        student.getSubjects()
                .stream()
                .forEach(subject -> subject.getStudents().remove(student));
        
        student.setSubjects(null);
        
        Arrays.asList(req.getParameter("subjects")) //adding
                .stream()
                .map(subject -> Integer.parseInt(subject))
                .map(subject -> (Subject) session.get(Subject.class, subject))
                .forEach(subject -> student.addSubject(subject));
        
        student.setFName(fName);//adding
        student.setNumber(Long.parseLong(number));//adding
        student.setLName(lName);//adding
        student.setRollNumber(rollnumber);//adding

    }
    
}
