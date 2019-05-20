/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.Teacher;
import org.hibernate.Session;
import utility.PostBackController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 *
 * @author sukhvir
 */
@WebServlet("/admin/teachers/deleteteacher")
public class DeleteTeacher extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        int teacherId = Integer.parseInt(req.getParameter("teacherId"));
        Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);
        if (teacher.isUnaccounted()) {

            session.delete(teacher);
            resp.sendRedirect("/OAS/admin/teacherd");
        } else {
            resp.sendRedirect("/OAS/admin/teachers/detailteacher?teacherId=" + teacherId);
        }

    }

}
