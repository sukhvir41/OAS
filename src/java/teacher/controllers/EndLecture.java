/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher.controllers;

import entities.Lecture;
import entities.Teacher;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.Controller;
import utility.Utils;

/**
 *
 * @author icr
 */
@WebServlet(urlPatterns = "/teacher/endlecture")
public class EndLecture extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        Teacher teacher = (Teacher) req.getSession().getAttribute("teacher");
        String lectureId = req.getParameter("lectureId");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());
        Lecture lecture = (Lecture) session.get(Lecture.class, lectureId);
        if (teacher.getTeaches().contains(lecture.getTeaching())) {
            lecture.setEnded(true);
            resp.sendRedirect("/OAS/teacher?lectureId" + lectureId);
        } else {
            resp.sendRedirect("/OAS/teacher");
        }

    }

}
