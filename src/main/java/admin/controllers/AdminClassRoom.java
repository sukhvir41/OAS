/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import java.io.PrintWriter;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.EntityHelper;
import org.hibernate.Session;

import entities.ClassRoom;
import entities.Course;
import utility.Controller;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/classrooms")
public class AdminClassRoom extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        System.out.println("classrooms start");
        System.out.println("starting getting classrooms");


        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ClassRoom> query = builder.createQuery(ClassRoom.class);
        Root<ClassRoom> classRoot = query.from(ClassRoom.class);

        List<ClassRoom> classRooms = session.createQuery(query)
                .setReadOnly(true)
                .getResultList();


        /*List<ClassRoom> classRooms = EntityHelper.getAll(session, ClassRoom.class, true);*/


        System.out.println("got all classrooms");
        System.out.println("get all courses");
        //List<Course> courses = EntityHelper.getAll(session, Course.class, true);
        System.out.println("got all courses");
        // req.setAttribute("classrooms", classRooms);
        //req.setAttribute("courses", courses);

       /* req.getRequestDispatcher("/WEB-INF/admin/admin-classroom.jsp")
                .include(req, resp);*/
        System.out.println("classrooms end");
    }

}
