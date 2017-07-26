/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.admin;

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
        switch (action) {
            case "verify": {
                teacher.setVerified(Boolean.TRUE);
                out.print(Boolean.TRUE);
                break;
            }
            case "deverify": {
                teacher.setVerified(Boolean.FALSE);
                out.print(Boolean.TRUE);
                break;
            }
            default:
                out.print(Boolean.FALSE);
        }
    }

}
