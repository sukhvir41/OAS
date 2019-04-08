/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.ClassRoom;
import entities.Subject;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/subjects/detailsubject")
public class DetailSubject extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int subjectId = Integer.parseInt(req.getParameter("subjectId"));

        Subject subject = (Subject) session.get(Subject.class, subjectId);
        List<ClassRoom> classRooms = subject.getCourse().getClassRooms();
        List<ClassRoom> classroom = new ArrayList<>();
        classRooms.stream()
                .filter(e -> !subject.getClassRooms().contains(e))
                .forEach(classroom::add);

        req.setAttribute("classrooms", classroom);
        req.setAttribute("subject", subject);

        req.getRequestDispatcher("/WEB-INF/admin/detailsubject.jsp").include(req, resp);

    }
}
