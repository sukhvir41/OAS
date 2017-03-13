/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.hod;

import entities.ClassRoom;
import entities.Course;
import entities.Department;
import entities.Subject;
import entities.Teacher;
import entities.Teaching;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/hod/teachersubjectmapping")
public class TCSassigner extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
    
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            Teacher hod = (Teacher) req.getSession().getAttribute("teacher");
            Department department = (Department) req.getSession().getAttribute("department");
            
            hod = (Teacher) session.get(Teacher.class, hod.getId());
            department = (Department) session.get(Department.class, department.getId());
            List<Teacher> teachers = department.getTeachers();
            
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
            req.getRequestDispatcher("/WEB-INF/hod/teachersubjectmapping.jsp").forward(req, resp);
            
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
            resp.sendRedirect("/OAS/error");
        }
    }
    
}