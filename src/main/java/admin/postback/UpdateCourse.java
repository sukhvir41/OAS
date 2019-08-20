/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.PostBackController;
import utility.UrlParameters;

import javax.persistence.criteria.JoinType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/courses/update-course")
public class UpdateCourse extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var courseIdString = req.getParameter("courseId");
        String name = req.getParameter("courseName");

        if (StringUtils.isAnyBlank(name, courseIdString)) {
            onError(req, resp);
            return;
        }

        long courseId = Long.parseLong(courseIdString);

        Course course = EntityHelper.getInstance(courseId, Course_.id, Course.class, session, false, Course_.DEPARTMENT);
        course.setName(name);

        var departmentIdString = req.getParameter("departmentId");
        if (StringUtils.isNotBlank(departmentIdString)) {
            var departmentId = Long.parseLong(departmentIdString);
            if (course.getDepartment().getId() != departmentId) {
                // change course department if it is different
            }
        }

        UrlParameters parameters = new UrlParameters()
                .addSuccessParameter()
                .addParameter("courseId", courseId)
                .addMessage("Course name updated");

        resp.sendRedirect(
                parameters.getUrl("/OAS/admin/courses/course-details")
        );

    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var url = new UrlParameters()
                .addErrorParameter();
        var courseId = req.getParameter("courseId");

        if (StringUtils.isBlank(courseId)) {
            url.addMessage("The course you are tying to update does not exist");
            resp.sendRedirect(url.getUrl("/OAS/admin/courses"));
        } else {
            url.addMessage("Please provide the necessary data");
        }

        resp.sendRedirect(
                new UrlParameters()
                        .addErrorParameter()
                        .addParameter("courseId", req.getParameter("courseId"))
                        .addMessage("Unable to edit the course as details were missing")
                        .getUrl("/OAS/admin/courses")
        );
    }

    public void changeDepartment(Course course, long departmentId, Session session) {
        //changing the department steps
        // 1. remove all the class teachers from classrooms belonging to this course who are the registered in the new department
        // 2 remove any teacher form teaching mapping that are not registered to the new department
        // 3. change the department of the course

        /*
         * select all teacher inner join classroom inner course where teacher department not in department set teacher classroom as null
         * will jooq be easier
         * */
        var holder = CriteriaHolder.getUpdateHolder(session, Teacher.class);

        var subQuery = holder.getQuery()
                .subquery(UUID.class);
        var subQueryFrom = subQuery.from(Teacher.class);

        var departmentJoin = subQueryFrom.join(Teacher_.departments, JoinType.INNER);
        departmentJoin.on(
                holder.getBuilder()
                        .equal(subQueryFrom.get(Teacher_.departments), departmentJoin.get(TeacherDepartmentLink_.department)),
                holder.getBuilder()
                        .notEqual(departmentJoin.get(TeacherDepartmentLink_.department), departmentId)
        );

        var classRoomJoin = subQueryFrom.join(Teacher_.classRoom, JoinType.INNER);
        var courseJoin = classRoomJoin.join(ClassRoom_.course, JoinType.INNER);
        courseJoin.on(
                holder.getBuilder()
                        .equal(classRoomJoin.get(ClassRoom_.course), courseJoin.get(Course_.id)),
                holder.getBuilder()
                        .equal(classRoomJoin.get(ClassRoom_.course), course)
        );

        subQuery.select(subQueryFrom.get(Teacher_.id));

        holder.getQuery()
                .set(
                        Teacher_.classRoom,
                        holder.getBuilder()
                                .nullLiteral(ClassRoom.class)
                )
                .where(
                        holder.getRoot().get(Teacher_.id)
                                .in(subQuery)
                );

        session.createQuery(holder.getQuery())
                .executeUpdate();

    }


}
