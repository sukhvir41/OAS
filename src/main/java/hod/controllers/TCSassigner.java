/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hod.controllers;

import entities.*;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/hod/teachersubjectmapping")
public class TCSassigner extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        Teacher hod = (Teacher) req.getSession().getAttribute("teacher");
        Department department = (Department) req.getSession().getAttribute("department");

        hod = (Teacher) session.get(Teacher.class, hod.getId());
        department = (Department) session.get(Department.class, department.getId());
        Set<Teacher> teachers = department.getTeachers();

        for (Course course : department.getCourses()) {
            for (ClassRoom classRoom : course.getClassRooms()) {
                for (Subject subject : classRoom.getSubjects()) {
                    List<Teaching> teachings = session.createCriteria(Teaching.class)
                            .add(Restrictions.eq("classRoom", classRoom))
                            .add(Restrictions.eq("subject", subject)).list();
                    if (teachings.size() <= 0) {
                        Teaching teaching = new Teaching(classRoom, subject);
                        session.save(teaching);
                    }
                }
            }
        }

        List<Teaching> unregistredTeaching = session.createCriteria(Teaching.class)
                .add(Restrictions.isNull("teacher")).list();

        req.setAttribute("department", department);
        req.setAttribute("teachers", teachers);
        req.setAttribute("teachings", unregistredTeaching);

        req.getRequestDispatcher("/WEB-INF/hod/teachersubjectmapping.jsp").include(req, resp);

    }

}
