/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.ajax;

import entities.Teacher;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.AjaxController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/teachers/activateordeactivateteacher")
public class ActivateOrDeactivateTeacher extends AjaxController {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        
        String action = req.getParameter("action");
        long teacherId = Long.parseLong(req.getParameter("teacherId"));
        Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);
        teacher.setUnaccounted(false);
        switch (action) {
            case "verify": {
                teacher.setVerified(true);
                out.print(true);
                break;
            }
            case "deverify": {
                teacher.setVerified(false);
                out.print(true);
                break;
            }
            default:
                out.print(false);
        }
    }
    
}
