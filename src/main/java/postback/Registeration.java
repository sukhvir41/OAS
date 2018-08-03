/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback;

import entities.ClassRoom;
import entities.Department;
import entities.Student;
import entities.Subject;
import entities.Teacher;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/registerpost")
public class Registeration extends PostBackController {

    //validation done in filter
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String email = req.getParameter("email");
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        long number = Long.parseLong(req.getParameter("number"));
        String type = req.getParameter("type");
        int rollNumber = 0; 
        long classId = 0;
        ArrayList<String> subjects;
        ArrayList<String> departments;
        String hod = null;

        if (type.equals("student")) {
            subjects = new ArrayList<>(Arrays.asList(req.getParameterValues("subject")));
            classId = Long.parseLong(req.getParameter("class"));
            rollNumber = Integer.parseInt(req.getParameter("rollnumber"));

            ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classId);
            Student student = new Student(rollNumber, type, lastName, classRoom, userName, password, email, number);

            session.save(student);
            subjects.stream()
                    .map(Long::parseLong)
                    .map(subjectId -> (Subject) session.get(Subject.class, subjectId))
                    .forEachOrdered(student::addSubject);
            student.addClassRoom(classRoom);

        } else {
            departments = new ArrayList<>(Arrays.asList(req.getParameterValues("department")));

            Teacher teacher = new Teacher(firstName, lastName, userName, password, email, number);
            session.save(teacher);

            departments.stream()
                    .map(Long::parseLong)
                    .map(e -> (Department) session.get(Department.class, e))
                    .forEachOrdered(teacher::addDepartment);
        }
        resp.sendRedirect("login");
    }
}
