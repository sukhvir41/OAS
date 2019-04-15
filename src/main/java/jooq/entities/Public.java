/*
 * This file is generated by jOOQ.
 */
package jooq.entities;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1790262939;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.admin</code>.
     */
    public final Admin ADMIN = jooq.entities.tables.Admin.ADMIN;

    /**
     * The table <code>public.attendance</code>.
     */
    public final Attendance ATTENDANCE = jooq.entities.tables.Attendance.ATTENDANCE;

    /**
     * The table <code>public.class_room</code>.
     */
    public final ClassRoom CLASS_ROOM = jooq.entities.tables.ClassRoom.CLASS_ROOM;

    /**
     * The table <code>public.course</code>.
     */
    public final Course COURSE = jooq.entities.tables.Course.COURSE;

    /**
     * The table <code>public.department</code>.
     */
    public final Department DEPARTMENT = jooq.entities.tables.Department.DEPARTMENT;

    /**
     * The table <code>public.lecture</code>.
     */
    public final Lecture LECTURE = jooq.entities.tables.Lecture.LECTURE;

    /**
     * The table <code>public.student</code>.
     */
    public final Student STUDENT = jooq.entities.tables.Student.STUDENT;

    /**
     * The table <code>public.student_subject_link</code>.
     */
    public final StudentSubjectLink STUDENT_SUBJECT_LINK = jooq.entities.tables.StudentSubjectLink.STUDENT_SUBJECT_LINK;

    /**
     * The table <code>public.subject</code>.
     */
    public final Subject SUBJECT = jooq.entities.tables.Subject.SUBJECT;

    /**
     * The table <code>public.subject_class_link</code>.
     */
    public final SubjectClassLink SUBJECT_CLASS_LINK = jooq.entities.tables.SubjectClassLink.SUBJECT_CLASS_LINK;

    /**
     * The table <code>public.tcs</code>.
     */
    public final Tcs TCS = jooq.entities.tables.Tcs.TCS;

    /**
     * The table <code>public.teacher</code>.
     */
    public final Teacher TEACHER = jooq.entities.tables.Teacher.TEACHER;

    /**
     * The table <code>public.teacher_department_link</code>.
     */
    public final TeacherDepartmentLink TEACHER_DEPARTMENT_LINK = jooq.entities.tables.TeacherDepartmentLink.TEACHER_DEPARTMENT_LINK;

    /**
     * The table <code>public.users</code>.
     */
    public final Users USERS = jooq.entities.tables.Users.USERS;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.HIBERNATE_SEQUENCE);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Admin.ADMIN,
            Attendance.ATTENDANCE,
            ClassRoom.CLASS_ROOM,
            Course.COURSE,
            Department.DEPARTMENT,
            Lecture.LECTURE,
            Student.STUDENT,
            StudentSubjectLink.STUDENT_SUBJECT_LINK,
            Subject.SUBJECT,
            SubjectClassLink.SUBJECT_CLASS_LINK,
            Tcs.TCS,
            Teacher.TEACHER,
            TeacherDepartmentLink.TEACHER_DEPARTMENT_LINK,
            Users.USERS);
    }
}
