/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hod.ajax;

import entities.Department;
import entities.Teacher;
import org.hibernate.Session;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/hod/ajax/activateordeactivateteacher")
public class ActivateOrDeactivateTeacher extends AjaxController {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int teacherId = Integer.parseInt(req.getParameter("teacherId"));
        String action = req.getParameter("action");
        
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());
        
        Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);
        if (teacher.getDepartment().contains(department)) {
            if (action.equals("verify")) {
                teacher.setVerified(true);
            } else {
                teacher.setVerified(false);
            }
            out.print(true);
        } else {
            out.print(false);
        }
    }
    
}
