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
import utility.CriteriaHolder;
import utility.UrlBuilder;

import javax.persistence.criteria.JoinType;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

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
                    new UrlBuilder()
                            .addErrorParameter()
                            .addMessage("The lecture you are trying to access does not exist")
                            .getUrl("/OAS/admin/lectures")
            );
            return;
        }

        var lecture = session.get(Lecture.class, lectureId);

        List<Student> presentStudents = getPresentStudents(lectureId, session);
        List<Student> absentStudents = getAbsentStudents(lectureId, session);

        req.setAttribute("total", presentStudents.size() + absentStudents.size());// setting the total size first as the list will have only absent students later

        req.setAttribute("presentStudents", presentStudents);
        req.setAttribute("absentStudents", absentStudents);
        req.setAttribute("headcount", presentStudents.size());
        req.setAttribute("lecture", lecture);
        req.getRequestDispatcher("/WEB-INF/admin/lecture-details.jsp")
                .include(req, resp);
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


    private List<Student> getAbsentStudents(String lectureId, Session session) {

        var criteriaStudent = CriteriaHolder.getQueryHolder(session, Student.class);

        var lectureAttendedSubQuery = criteriaStudent.getQuery()
                .subquery(Student.class);
        var lectureRoot = lectureAttendedSubQuery.from(Lecture.class);
        var attendanceJoin = lectureRoot.join(Lecture_.attendances, JoinType.INNER);
        lectureAttendedSubQuery.where(
                criteriaStudent.getCriteriaBuilder()
                        .and(
                                criteriaStudent.getCriteriaBuilder()
                                        .equal(lectureRoot.get(Lecture_.id), lectureId),
                                criteriaStudent.getCriteriaBuilder()
                                        .equal(attendanceJoin.get(Attendance_.attended), true)
                        )
        );
        lectureAttendedSubQuery.select(attendanceJoin.get(Attendance_.student));

        var lectureSubQuery = criteriaStudent.getQuery()
                .subquery(Lecture.class);
        var lectureSubQueryRoot = lectureSubQuery.from(Lecture.class);
        lectureSubQuery.where(
                criteriaStudent.getCriteriaBuilder()
                        .equal(lectureSubQueryRoot.get(Lecture_.id), lectureId)
        );

        var teachingClassRoomSubQuery = criteriaStudent.getQuery()
                .subquery(ClassRoom.class);
        var teachingClassRoomRoot = teachingClassRoomSubQuery.
                from(Teaching.class);
        teachingClassRoomSubQuery.where(
                criteriaStudent.getCriteriaBuilder()
                        .equal(teachingClassRoomRoot.get(Teaching_.lectures), lectureSubQuery)
        );
        teachingClassRoomSubQuery.select(teachingClassRoomRoot.get(Teaching_.classRoom));

        var teachingSubjectSubQuery = criteriaStudent.getQuery()
                .subquery(Subject.class);
        var teachingSubjectRoot = teachingClassRoomSubQuery.
                from(Teaching.class);
        teachingClassRoomSubQuery.where(
                criteriaStudent.getCriteriaBuilder()
                        .equal(teachingSubjectRoot.get(Teaching_.lectures), lectureSubQuery)
        );
        teachingSubjectSubQuery.select(teachingSubjectRoot.get(Teaching_.subject));

        criteriaStudent.getQuery()
                .where(
                        criteriaStudent.getCriteriaBuilder()
                                .and(
                                        criteriaStudent.getCriteriaBuilder()
                                                .not(
                                                        criteriaStudent.getCriteriaBuilder()
                                                                .in(lectureAttendedSubQuery)
                                                ),
                                        criteriaStudent.getCriteriaBuilder().equal(
                                                criteriaStudent.getRoot()
                                                        .get(Student_.classRoom),
                                                teachingClassRoomSubQuery
                                        ),
                                        criteriaStudent.getRoot()
                                                .get(Student_.subjects)
                                                .in(teachingSubjectSubQuery)
                                )
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
