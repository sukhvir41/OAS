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
        int rollNumber = 0, courseId = 0, classId = 0;
        String[] subjects = null;
        String[] departments = null;
        String hod = null;
        System.out.println(type);
        //System.out.println(req.getParameterValues("subject"));
        if (type.equals("student")) {
            rollNumber = Integer.parseInt(req.getParameter("rollnumber"));
            courseId = Integer.parseInt(req.getParameter("course"));
            classId = Integer.parseInt(req.getParameter("class"));
            subjects = req.getParameterValues("subject");
        } else {
            departments = req.getParameterValues("department");
            hod = req.getParameter("hod");
        }
        PrintWriter out = resp.getWriter();
        if (type.equals("student")) {
            out.print(firstName + "\n" + lastName + "\n" + email + "\n" + userName + "\n" + password + "\n" + number + "\n" + type + "\n" + rollNumber + "\n" + courseId + "\n" + classId);
            for (int i = 0; i < subjects.length; i++) {
                out.print("\n");
                out.print(subjects[i]);
            }
        } else {
            out.print(firstName + "\n" + lastName + "\n" + email + "\n" + userName + "\n" + password + "\n" + number + "\n" + type + "\n" + hod);
            for (int i = 0; i < departments.length; i++) {
                out.print("\n");
                out.print(departments[i]);
            }
        }
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("error");
        out.close();
    }
}
