/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.hod;

import entities.Teacher;
import entities.Teaching;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
//### remove subject from teachrer and not assisgn is left
@WebServlet(urlPatterns = "/teacher/hod/teachersubjectmappingpost")
public class TeacherSubjectMapping extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
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

            session.getTransaction().commit();
            session.close();
            out.print("true");
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
            out.print("false");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void addTeacher(Teaching e, Teacher teacher) {
        if (e.getTeacher() != null) {
            Teacher teacherTemp = e.getTeacher();
            teacherTemp.getTeaches().remove(e);
            e.setTeacher(null);
        }
        e.addTeacher(teacher);
    }

}
