/*
 * This file is generated by jOOQ.
 */
package jooq.entities;


import javax.annotation.Generated;

import jooq.entities.tables.Admin;
import jooq.entities.tables.Attendance;
import jooq.entities.tables.ClassRoom;
import jooq.entities.tables.Course;
import jooq.entities.tables.Department;
import jooq.entities.tables.Lecture;
import jooq.entities.tables.Student;
import jooq.entities.tables.StudentSubjectLink;
import jooq.entities.tables.Subject;
import jooq.entities.tables.SubjectClassLink;
import jooq.entities.tables.Tcs;
import jooq.entities.tables.Teacher;
import jooq.entities.tables.TeacherDepartmentLink;
import jooq.entities.tables.Users;


/**
 * Convenience access to all tables in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.admin</code>.
     */
    public static final Admin ADMIN = jooq.entities.tables.Admin.ADMIN;

    /**
     * The table <code>public.attendance</code>.
     */
    public static final Attendance ATTENDANCE = jooq.entities.tables.Attendance.ATTENDANCE;

    /**
     * The table <code>public.class_room</code>.
     */
    public static final ClassRoom CLASS_ROOM = jooq.entities.tables.ClassRoom.CLASS_ROOM;

    /**
     * The table <code>public.course</code>.
     */
    public static final Course COURSE = jooq.entities.tables.Course.COURSE;

    /**
     * The table <code>public.department</code>.
     */
    public static final Department DEPARTMENT = jooq.entities.tables.Department.DEPARTMENT;

    /**
     * The table <code>public.lecture</code>.
     */
    public static final Lecture LECTURE = jooq.entities.tables.Lecture.LECTURE;

    /**
     * The table <code>public.student</code>.
     */
    public static final Student STUDENT = jooq.entities.tables.Student.STUDENT;

    /**
     * The table <code>public.student_subject_link</code>.
     */
    public static final StudentSubjectLink STUDENT_SUBJECT_LINK = jooq.entities.tables.StudentSubjectLink.STUDENT_SUBJECT_LINK;

    /**
     * The table <code>public.subject</code>.
     */
    public static final Subject SUBJECT = jooq.entities.tables.Subject.SUBJECT;

    /**
     * The table <code>public.subject_class_link</code>.
     */
    public static final SubjectClassLink SUBJECT_CLASS_LINK = jooq.entities.tables.SubjectClassLink.SUBJECT_CLASS_LINK;

    /**
     * The table <code>public.tcs</code>.
     */
    public static final Tcs TCS = jooq.entities.tables.Tcs.TCS;

    /**
     * The table <code>public.teacher</code>.
     */
    public static final Teacher TEACHER = jooq.entities.tables.Teacher.TEACHER;

    /**
     * The table <code>public.teacher_department_link</code>.
     */
    public static final TeacherDepartmentLink TEACHER_DEPARTMENT_LINK = jooq.entities.tables.TeacherDepartmentLink.TEACHER_DEPARTMENT_LINK;

    /**
     * The table <code>public.users</code>.
     */
    public static final Users USERS = jooq.entities.tables.Users.USERS;
}
