/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Teacher;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/teachers/unaccountteacher")
public class UnaccountTeacher extends Controller {
    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int teacherId = Integer.parseInt(req.getParameter("teacherId"));
        
        Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);
        if (teacher.isVerified()) {
            resp.sendRedirect("/OAS/admin/teachers/detailteacher?teacherId=" + teacherId);
        } else {
            teacher.setUnaccounted(true);
            
            teacher.getTeaches()
                    .stream()
                    .forEach(teaching -> teaching.setTeacher(null));
            
            teacher.getTeaches().clear();
            
            if(teacher.getClassRoom()!=null){
                teacher.getClassRoom().setClassTeacher(null);
            }
            
            if(teacher.isHod()){
                teacher.setHod(false);
                teacher.getHodOf()
                        .stream()
                        .forEach(department -> department.setHod(null));
                teacher.getHodOf().clear();
            }
            
            teacher.getDepartment()
                    .stream()
                    .forEach(department -> department.getTeachers().remove(teacher));
            
            teacher.getDepartment().clear();
            resp.sendRedirect("/OAS/admin/teachers");
        }
    }
    
}
