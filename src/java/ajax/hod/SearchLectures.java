/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.hod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.ClassRoom;
import entities.Department;
import entities.Lecture;
import entities.Subject;
import entities.Teaching;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/hod/ajax/searchLectures")
public class SearchLectures extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());

        int classId = Integer.parseInt(req.getParameter("classroomId"));
        String subjectId = req.getParameter("subjectId");
        //LocalDateTime start = LocalDateTime.parse(req.getParameter("startdate"), DateTimeFormatter.) todo: fix date evrywhere
        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classId);

        if (classRoom.getCourse().getDepartment().getId() == department.getId()) {
            List<Teaching> teaching;
            if (subjectId.equals("all")) {
                teaching = session.createCriteria(Teaching.class)
                        .add(Restrictions.eq("classRoom", classRoom))
                        .list();

            } else {
                Subject subject = (Subject) session.get(Subject.class, Integer.parseInt(subjectId));
                teaching = session.createCriteria(Teaching.class)
                        .add(Restrictions.eq("subject", subject))
                        .add(Restrictions.eq("classRoom", classRoom))
                        .list();

            }

            List<Lecture> lectures = session.createCriteria(Lecture.class)
                    .add(Restrictions.in("teaching", teaching))
                    .addOrder(Order.desc("date"))
                    //add date here     .add()
                    .list();

            JsonArray jsonLectures = new JsonArray();

            lectures.stream()
                    .forEach(lecture -> add(jsonLectures, lecture));

            Gson gson = new Gson();
            out.print(gson.toJson(jsonLectures));

        } else {

            resp.sendRedirect("/OAS/error");

        }

    }

    private void add(JsonArray jsonLectures, Lecture theLecture) {

        int attendentStudent = theLecture.getAttendance()
                .stream()
                .filter(attendance -> attendance.isAttended())
                .collect(Collectors.toList())
                .size();
        int totalStudents = theLecture.getTeaching()
                .getClassRoom()
                .getStudents()
                .stream()
                .filter(student -> student.getSubjects().contains(theLecture.getTeaching().getSubject()))
                .collect(Collectors.toList())
                .size();

        JsonObject lecture = new JsonObject();
        lecture.addProperty("id", theLecture.getId());
        lecture.addProperty("class", theLecture.getTeaching().getClassRoom().toString());
        lecture.addProperty("subject", theLecture.getTeaching().getSubject().toString());
        lecture.addProperty("count", theLecture.getCount());
        lecture.addProperty("date", theLecture.getDate().toString());
        lecture.addProperty("present", attendentStudent);
        lecture.addProperty("absent", totalStudents - attendentStudent);

        jsonLectures.add(lecture);

    }

}
