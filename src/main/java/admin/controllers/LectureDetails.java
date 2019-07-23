/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.Controller;
import utility.UrlParameters;

import javax.persistence.criteria.JoinType;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/lectures/lecture-details")
public class LectureDetails extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {


        var lectureId = req.getParameter("lectureId");

        if (StringUtils.isBlank(lectureId)) {
            resp.sendRedirect(
                    new UrlParameters()
                            .addErrorParameter()
                            .addMessage("The lecture you are trying to access does not exist")
                            .getUrl("/OAS/admin/lectures")
            );
            return;
        }


        List<Student> presentStudents = getPresentStudents(lectureId, session);


        String lectureId = req.getParameter("lectureId");
        Lecture lecture = (Lecture) session.get(Lecture.class, lectureId);

        List<Student> students = lecture.getTeaching()// this will contain all studentts first and then only absent students
                .getClassRoom()
                .getStudents()
                .stream()
                .filter(student -> student.getSubjects().contains(lecture.getTeaching().getSubject()))
                .collect(Collectors.toList());

        Collections.sort(students);// sorting all studnets according to their roll number

        req.setAttribute("total", students.size());// setting the total size first as the list will have only absent students later

        // todo: write sql for this
       /* List<Student> present = lecture.getAttendance()
                .stream()
                .filter(attendance -> attendance.isAttended())
                .map(attendance -> attendance.getStudent())
                .collect(Collectors.toList());
        students.removeAll(present);//removing all present students from the students list

        Collections.sort(present);// sorting all students accordig to their to roll number
*/
        // todo: write sql for this
        //req.setAttribute("present", present);
        req.setAttribute("absent", students);
        // todo: write sql for this
        //req.setAttribute("headcount", present.size());
        req.setAttribute("lecture", lecture);
        req.getRequestDispatcher("/WEB-INF/admin/detaillecture.jsp").include(req, resp);
    }

    private List<Student> getPresentStudents(String lectureId, Session session) {

        var criteriaStudent = CriteriaHolder.getQueryHolder(session, Student.class);

        var lectureSubQuery = criteriaStudent.getQuery()
                .subquery(Student.class);

        var lectureRoot = lectureSubQuery.from(Lecture.class);

        var attendanceJoin = lectureRoot.join(Lecture_.attendances, JoinType.INNER);

        lectureSubQuery.where(
                criteriaStudent.getCriteriaBuilder()
                        .and(
                                criteriaStudent.getCriteriaBuilder().equal(lectureRoot.get(Lecture_.id), lectureId),
                                criteriaStudent.getCriteriaBuilder().equal(attendanceJoin.get(Attendance_.attended), true)
                        )
        );

        lectureSubQuery.select(attendanceJoin.get(Attendance_.student));

        criteriaStudent.getQuery()
                .where(
                        criteriaStudent.getCriteriaBuilder().in(lectureSubQuery)
                )
                .orderBy(
                        criteriaStudent.getCriteriaBuilder()
                                .asc(
                                        criteriaStudent.getRoot()
                                                .get(Student_.rollNumber)
                                )
                );

        return session.createQuery(criteriaStudent.getQuery())
                .setReadOnly(true)
                .getResultList();
    }

}
