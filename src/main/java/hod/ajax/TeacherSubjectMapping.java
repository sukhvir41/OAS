/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hod.ajax;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Teacher;
import entities.Teaching;
import utility.AjaxController;

/**
 *
 * @author sukhvir
 */
//### remove subject from teachrer and not assisgn is left
@WebServlet(urlPatterns = "/teacher/hod/teachersubjectmappingpost")
public class TeacherSubjectMapping extends AjaxController {

    private void addTeacher(Teaching e, Teacher teacher) {
        if (e.getTeacher() != null) {
            Teacher teacherTemp = e.getTeacher();
            teacherTemp.getTeaches().remove(e);
            e.setTeacher(null);
        }
        e.addTeacher(teacher);
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        Enumeration name = req.getParameterNames();
        while (name.hasMoreElements()) {
            String tempname = name.nextElement().toString();
            int teacherId = Integer.valueOf(tempname.replace("[]", ""));
            Teacher teacher = (Teacher) session.get(Teacher.class, teacherId);
            List<String> values = Arrays.asList(req.getParameterValues(String.valueOf(tempname)));
            values.stream()
                    .map(Integer::valueOf)
                    .map(e -> (Teaching) session.get(Teaching.class, e))
                    .forEach(e -> addTeacher(e, teacher));
        }

        out.print("true");

    }

}
