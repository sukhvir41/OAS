/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback;

import entities.ClassRoom;
import entities.Course;
import entities.Department;
import entities.Login;
import entities.Student;
import entities.Subject;
import entities.Teacher;
import entities.UserType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/registerpost")
public class Registeration extends HttpServlet {

    //validation done in filter
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String email = req.getParameter("email");
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        long number = Long.parseLong(req.getParameter("number"));
        String type = req.getParameter("type");
        int rollNumber = 0, classId = 0;
        ArrayList<String> subjects;
        ArrayList<String> departments;
        String hod = null;
        if (type.equals("student")) {
            subjects = new ArrayList<>(Arrays.asList(req.getParameterValues("subject")));
            classId = Integer.parseInt(req.getParameter("class"));
            rollNumber = Integer.parseInt(req.getParameter("rollnumber"));
            Student student = new Student(rollNumber, firstName, lastName, number, email);
            Session session = Utils.openSession();
            session.beginTransaction();
            ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classId);
            session.save(student);
            subjects.stream()
                    .map(Integer::parseInt)
                    .map(e -> (Subject) session.get(Subject.class, e))
                    .forEachOrdered(student::addSubject);
            student.addClassRoom(classRoom);
            Login login = Login.createStudentLogin(userName, password, student.getId(), email);
            session.save(login);
            session.getTransaction().commit();
            session.close();
            resp.sendRedirect("login");
        } else {
            departments = new ArrayList<>(Arrays.asList(req.getParameterValues("department")));
            Session session = Utils.openSession();
            session.beginTransaction();
            Teacher teacher;
            teacher = new Teacher(firstName, lastName, number, email, false);
            session.save(teacher);
            departments.stream()
                    .map(Integer::parseInt)
                    .map(e -> (Department) session.get(Department.class, e))
                    .forEachOrdered(teacher::addDepartment);
            Login login = Login.createTeacherLogin(userName, password, teacher.getId(), email);
            session.save(login);
            session.getTransaction().commit();
            session.close();
            resp.sendRedirect("login");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("error");
        out.close();
    }
}
