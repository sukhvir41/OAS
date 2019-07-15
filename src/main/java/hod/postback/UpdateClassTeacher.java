/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hod.postback;

import entities.ClassRoom;
import entities.Department;
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
@WebServlet(urlPatterns = "/teacher/hod/classrooms/updateclassteacher")
public class UpdateClassTeacher extends PostBackController {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int classroomId = Integer.parseInt(req.getParameter("classroomId"));
        int teacherId = Integer.parseInt(req.getParameter("teacherId"));
        
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());
        
        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
        Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);
        
       /* if (department.getId() == classRoom.getCourse().getDepartment().getId() && teacher.getDepartment().contains(department)) {
            if (teacher.getClassRoom() == null) {
                if (classRoom.getClassTeacher() == null) {
                    teacher.setClassRoom(classRoom);
                    classRoom.setClassTeacher(teacher);
                } else {
                    classRoom.getClassTeacher().setClassRoom(null);
                    classRoom.setClassTeacher(null);
                    classRoom.setClassTeacher(teacher);
                    teacher.setClassRoom(classRoom);
                }
            } else {
                teacher.getClassRoom().setClassTeacher(null);
                teacher.setClassRoom(null);
                if (classRoom.getClassTeacher() == null) {
                    classRoom.setClassTeacher(teacher);
                    teacher.setClassRoom(classRoom);
                } else {
                    classRoom.getClassTeacher().setClassRoom(null);
                    classRoom.setClassTeacher(null);
                    classRoom.setClassTeacher(teacher);
                    teacher.setClassRoom(classRoom);
                }
                
            }
            resp.sendRedirect("/OAS/teacher/hod/classrooms/detailclassroom?classroomId=" + classroomId);
        } else {
            resp.sendRedirect("/OAS/error");
        }*/
        
    }
    
}
