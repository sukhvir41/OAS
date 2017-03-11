/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.teacher;

import entities.Lecture;
import entities.Teacher;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author icr
 */
@WebServlet(urlPatterns = "/teacher/endlecture")
public class EndLecture extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.getTransaction().begin();
        try {
            Teacher teacher = (Teacher) req.getSession().getAttribute("teacher");
            int lectureId = Integer.parseInt(req.getParameter("lectureId"));
            teacher = (Teacher) session.get(Teacher.class, teacher.getId());
            Lecture lecture = (Lecture) session.get(Lecture.class, lectureId);
            if (teacher.getTeaches().contains(lecture.getTeaching())) {
                lecture.setEnded(true);
                resp.sendRedirect("/OAS/teacher?lectureId" + lectureId);
            } else {
                resp.sendRedirect("/OAS/teacher");
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            resp.sendRedirect("/OAS/error");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
