/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.Course;
import entities.Course_;
import entities.EntityHelper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.PostBackController;
import utility.UrlBuilder;
import utility.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import static jooq.entities.Tables.*;

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
                changeDepartment(course, departmentId, session);
            }
        }

        UrlBuilder parameters = new UrlBuilder()
                .addSuccessParameter()
                .addParameter("courseId", courseId)
                .addMessage("Course name updated");

        resp.sendRedirect(
                parameters.getUrl("/OAS/admin/courses/course-details")
        );

    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var url = new UrlBuilder()
                .addErrorParameter();
        var courseId = req.getParameter("courseId");

        if (StringUtils.isBlank(courseId)) {
            url.addMessage("The course you are tying to update does not exist");
            resp.sendRedirect(url.getUrl("/OAS/admin/courses"));
        } else {
            url.addMessage("Please provide the necessary data")
                    .addParameter("courseId", courseId);

            resp.sendRedirect(
                    url.getUrl("/OAS/admin/courses/edit-course")
            );
        }
    }

    public void changeDepartment(Course course, long departmentId, Session session) {
        //changing the department steps
        // 1. remove all the class teachers from classrooms belonging to this course who are the registered in the new department
        // 2 remove any teacher form teaching mapping that are not registered to the new department
        // 3. change the department of the course

        removeClassTeachers(course, departmentId, session);
        removeTeachersFromTeaching(course, departmentId, session);
        updateTheCourse(course, departmentId, session);

    }

    private void removeClassTeachers(Course course, long departmentId, Session session) {
        var dsl = Utils.getDsl();
        var nativeQuery = dsl
                .delete(CLASS_TEACHER_CLASS_ROOM_LINK)
                .where(CLASS_TEACHER_CLASS_ROOM_LINK.CLASS_TEACHER_FID.in(
                        dsl.select(TEACHER_DEPARTMENT_LINK.TEACHER_FID)
                                .from(TEACHER_DEPARTMENT_LINK)
                                .innerJoin(CLASS_TEACHER_CLASS_ROOM_LINK)
                                .on(CLASS_TEACHER_CLASS_ROOM_LINK.CLASS_TEACHER_FID.eq(TEACHER_DEPARTMENT_LINK.TEACHER_FID))
                                .innerJoin(CLASS_ROOM)
                                .on(CLASS_ROOM.ID.eq(CLASS_TEACHER_CLASS_ROOM_LINK.CLASS_ROOM_FID).and(CLASS_ROOM.COURSE_FID.eq(course.getId())))
                                .where(TEACHER_DEPARTMENT_LINK.DEPARTMENT_FID.notEqual(departmentId))
                ));

        Utils.executeNativeQuery(session, nativeQuery);
    }

    public void removeTeachersFromTeaching(Course course, long departmentId, Session session) {
        var dsl = Utils.getDsl();

        var nativeQuery = dsl.update(TCS)
                .set(TCS.TEACHER_FID, (UUID) null)
                .where(TCS.TEACHER_FID.in(
                        dsl.select(TEACHER_DEPARTMENT_LINK.TEACHER_FID)
                                .from(TEACHER_DEPARTMENT_LINK)
                                .innerJoin(CLASS_TEACHER_CLASS_ROOM_LINK)
                                .on(CLASS_TEACHER_CLASS_ROOM_LINK.CLASS_TEACHER_FID.eq(TEACHER_DEPARTMENT_LINK.TEACHER_FID))
                                .innerJoin(CLASS_ROOM)
                                .on(CLASS_ROOM.ID.eq(CLASS_TEACHER_CLASS_ROOM_LINK.CLASS_ROOM_FID).and(CLASS_ROOM.COURSE_FID.eq(course.getId())))
                                .where(TEACHER_DEPARTMENT_LINK.DEPARTMENT_FID.notEqual(departmentId))
                ));

        Utils.executeNativeQuery(session, nativeQuery);
    }

    private void updateTheCourse(Course course, long departmentId, Session session) {

        var dsl = Utils.getDsl();

        var nativeQuery = dsl
                .update(COURSE)
                .set(COURSE.DEPARTMENT_FID, departmentId)
                .where(COURSE.ID.eq(course.getId()));

        Utils.executeNativeQuery(session, nativeQuery);
    }


}
