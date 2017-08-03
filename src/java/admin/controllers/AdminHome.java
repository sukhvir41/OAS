/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.ClassRoom;
import entities.Course;
import entities.Department;
import entities.Subject;
import utility.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin")
public class AdminHome extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        List<Department> departments;
        List<Course> courses;
        List<ClassRoom> classRooms;
        List<Subject> subjects;

        departments = (List<Department>) session.createQuery("from Department").list();
        courses = (List<Course>) session.createQuery("from Course").list();
        classRooms = (List<ClassRoom>) session.createQuery("from ClassRoom").list();
        subjects = (List<Subject>) session.createQuery("from Subject").list();
        req.setAttribute("departments", departments);
        req.setAttribute("courses", courses);
        req.setAttribute("classRooms", classRooms);
        req.setAttribute("subjects", subjects);

        req.getRequestDispatcher("WEB-INF/admin/adminhome.jsp").include(req, resp);
        extendCookie(req, resp);
    }

    private void extendCookie(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        try {
            if ((boolean) session.getAttribute("extenedCookie")) {
                session.setAttribute("extenedCookie", false);
                Cookie id = null, token = null;

                for (Cookie cookie : req.getCookies()) {
                    switch (cookie.getName()) {
                        case "sid":
                            id = cookie;
                            break;
                        case "stoken":
                            token = cookie;
                            break;
                    }
                }

                id.setMaxAge(864000);
                token.setMaxAge(864000);
                resp.addCookie(id);
                resp.addCookie(token);
            }

        } catch (Exception e) {
            System.out.println("error in extend cookie");
        }
    }
}
