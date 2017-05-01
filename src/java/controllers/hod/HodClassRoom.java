/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.hod;

import entities.ClassRoom;
import entities.Department;
import entities.Teacher;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/hod/classrooms")
public class HodClassRoom extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());
        
        List<ClassRoom> classRooms = new ArrayList<>();
        department.getCourses().stream()
                .forEach(course -> classRooms.addAll(course.getClassRooms()));
        
        req.setAttribute("classrooms", classRooms);
        req.getRequestDispatcher("/WEB-INF/hod/hodclassroom.jsp").include(req, resp);
    }

}
