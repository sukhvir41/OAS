/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.hod;

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
@WebServlet("/teacher/hod/classrooms/setclassteacher")
public class SetClassTeacher extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());

        int classrommId = Integer.parseInt(req.getParameter("classroomId"));
        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classrommId);

        if (classRoom.getCourse().getDepartment().getId() == department.getId()) {
            req.setAttribute("teachers", department.getTeachers());
            req.setAttribute("classroom", classRoom);
            req.getRequestDispatcher("/WEB-INF/hod/setclassteacher.jsp");

        } else {
            resp.sendRedirect("/OAS/error");

        }
    }

}
