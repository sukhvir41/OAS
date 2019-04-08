/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Teacher;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/teachers/unaacountteacher")
public class UnnaccoutTeacher extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        int teacherId = Integer.parseInt(req.getParameter("teacherId"));

        Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);

        if (!teacher.isVerified()) {
            teacher.getClassRoom().setClassTeacher(null);
            teacher.setClassRoom(null);

            teacher.getDepartment().stream()
                    .forEach(e -> e.getTeachers().remove(teacher));

            teacher.getHodOf().stream()
                    .forEach(e -> e.setHod(null));

            teacher.getTeaches().stream()
                    .forEach(e -> e.setTeacher(null));

            teacher.getTeaches().clear();

            teacher.setHod(false);

            teacher.unaccount();
        }
        resp.sendRedirect("/OAS/admin/teachers/detailteacher?teacherId=" + teacherId);

    }

}
