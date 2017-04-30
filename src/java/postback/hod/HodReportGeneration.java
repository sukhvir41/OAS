/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.hod;

import entities.ClassRoom;
import entities.Department;
import java.io.PrintWriter;
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
@WebServlet("/teacher/hod/generatereport")
public class HodReportGeneration extends PostBackController {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());
        
        int classrommId = Integer.parseInt(req.getParameter("classroomid"));
        ClassRoom classRomm = (ClassRoom) session.get(ClassRoom.class, classrommId);
        
        if (department.getId() == classRomm.getCourse().getDepartment().getId()) {
            req.getRequestDispatcher("postback.admin.GenerateReport").include(req, resp);
        } else {
            resp.sendRedirect("/OAS/error");
        }
    }
    
}
