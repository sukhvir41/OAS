/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hod.controllers;

import entities.ClassRoom;
import entities.Department;
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
@WebServlet(urlPatterns = "/teacher/hod/classrooms/detailclassroom")
public class DetailClassRoom extends Controller {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int classroomId = Integer.parseInt(req.getParameter("classroomId"));
        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
        
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());
        
        if (department.getId() == classRoom.getCourse().getDepartment().getId()) {
            req.setAttribute("classroom", classRoom);
            req.getRequestDispatcher("/WEB-INF/hod/detailclassroom.jsp").include(req, resp);
        } else {
            resp.sendRedirect("/OAS/error");
        }
    }
    
}
