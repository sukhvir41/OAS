/*
 * This file is generated by jOOQ.
 */
package jooq.entities.tables.records;


import java.util.UUID;

import javax.annotation.Generated;

import jooq.entities.tables.Attendance;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


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
public class AttendanceRecord extends UpdatableRecordImpl<AttendanceRecord> implements Record5<String, UUID, Boolean, Boolean, Boolean> {

    private static final long serialVersionUID = 1155802973;

    /**
     * Setter for <code>public.attendance.lecture_fid</code>.
     */
    public void setLectureFid(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.attendance.lecture_fid</code>.
     */
    public String getLectureFid() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.attendance.student_fid</code>.
     */
    public void setStudentFid(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.attendance.student_fid</code>.
     */
    public UUID getStudentFid() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.attendance.attended</code>.
     */
    public void setAttended(Boolean value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.attendance.attended</code>.
     */
    public Boolean getAttended() {
        return (Boolean) get(2);
    }

    /**
     * Setter for <code>public.attendance.leave</code>.
     */
    public void setLeave(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.attendance.leave</code>.
     */
    public Boolean getLeave() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>public.attendance.marked_by_teacher</code>.
     */
    public void setMarkedByTeacher(Boolean value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.attendance.marked_by_teacher</code>.
     */
    public Boolean getMarkedByTeacher() {
        return (Boolean) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<String, UUID> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<String, UUID, Boolean, Boolean, Boolean> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<String, UUID, Boolean, Boolean, Boolean> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Attendance.ATTENDANCE.LECTURE_FID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field2() {
        return Attendance.ATTENDANCE.STUDENT_FID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field3() {
        return Attendance.ATTENDANCE.ATTENDED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field4() {
        return Attendance.ATTENDANCE.LEAVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field5() {
        return Attendance.ATTENDANCE.MARKED_BY_TEACHER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getLectureFid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID component2() {
        return getStudentFid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component3() {
        return getAttended();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component4() {
        return getLeave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component5() {
        return getMarkedByTeacher();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getLectureFid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value2() {
        return getStudentFid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value3() {
        return getAttended();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value4() {
        return getLeave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value5() {
        return getMarkedByTeacher();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttendanceRecord value1(String value) {
        setLectureFid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttendanceRecord value2(UUID value) {
        setStudentFid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttendanceRecord value3(Boolean value) {
        setAttended(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttendanceRecord value4(Boolean value) {
        setLeave(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttendanceRecord value5(Boolean value) {
        setMarkedByTeacher(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttendanceRecord values(String value1, UUID value2, Boolean value3, Boolean value4, Boolean value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AttendanceRecord
     */
    public AttendanceRecord() {
        super(Attendance.ATTENDANCE);
    }

    /**
     * Create a detached, initialised AttendanceRecord
     */
    public AttendanceRecord(String lectureFid, UUID studentFid, Boolean attended, Boolean leave, Boolean markedByTeacher) {
        super(Attendance.ATTENDANCE);

        set(0, lectureFid);
        set(1, studentFid);
        set(2, attended);
        set(3, leave);
        set(4, markedByTeacher);
    }
}