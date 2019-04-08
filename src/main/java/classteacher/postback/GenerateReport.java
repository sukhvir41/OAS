/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classteacher.postback;

import java.io.OutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.ClassRoom;
import entities.Teacher;
import utility.ReportPostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/classteacher/generatereportpost")
public class GenerateReport extends ReportPostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, OutputStream out) throws Exception {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());

        int classroomId = Integer.parseInt(req.getParameter("classroom"));
        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
        if (teacher.getClassRoom().getId() == classRoom.getId()) {
            admin.postback.GenerateReport report = new admin.postback.GenerateReport();
            report.process(req, resp, session, httpSession, out);
        } else {
            resp.sendRedirect("/OAS/error");
        }
    }

}
