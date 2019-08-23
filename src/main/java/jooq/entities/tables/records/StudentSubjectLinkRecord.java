/*
 * This file is generated by jOOQ.
 */
package jooq.entities.tables.records;


import java.util.UUID;

import javax.annotation.Generated;

import jooq.entities.tables.StudentSubjectLink;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.12"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StudentSubjectLinkRecord extends UpdatableRecordImpl<StudentSubjectLinkRecord> implements Record2<UUID, Long> {

    private static final long serialVersionUID = 399355466;

    /**
     * Setter for <code>public.student_subject_link.student_fid</code>.
     */
    public void setStudentFid(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.student_subject_link.student_fid</code>.
     */
    public UUID getStudentFid() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.student_subject_link.subject_fid</code>.
     */
    public void setSubjectFid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.student_subject_link.subject_fid</code>.
     */
    public Long getSubjectFid() {
        return (Long) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<UUID, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<UUID, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<UUID, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return StudentSubjectLink.STUDENT_SUBJECT_LINK.STUDENT_FID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return StudentSubjectLink.STUDENT_SUBJECT_LINK.SUBJECT_FID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID component1() {
        return getStudentFid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component2() {
        return getSubjectFid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value1() {
        return getStudentFid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getSubjectFid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentSubjectLinkRecord value1(UUID value) {
        setStudentFid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentSubjectLinkRecord value2(Long value) {
        setSubjectFid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentSubjectLinkRecord values(UUID value1, Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached StudentSubjectLinkRecord
     */
    public StudentSubjectLinkRecord() {
        super(StudentSubjectLink.STUDENT_SUBJECT_LINK);
    }

    /**
     * Create a detached, initialised StudentSubjectLinkRecord
     */
    public StudentSubjectLinkRecord(UUID studentFid, Long subjectFid) {
        super(StudentSubjectLink.STUDENT_SUBJECT_LINK);

        set(0, studentFid);
        set(1, subjectFid);
    }
}
