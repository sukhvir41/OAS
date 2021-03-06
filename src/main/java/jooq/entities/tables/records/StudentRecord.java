/*
 * This file is generated by jOOQ.
 */
package jooq.entities.tables.records;


import jooq.entities.tables.Student;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.util.UUID;


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
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class StudentRecord extends UpdatableRecordImpl<StudentRecord> implements Record6<UUID, String, String, String, Integer, Long> {

    private static final long serialVersionUID = 2072060134;

    /**
     * Setter for <code>public.student.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.student.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.student.first_name</code>.
     */
    public void setFirstName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.student.first_name</code>.
     */
    public String getFirstName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.student.last_name</code>.
     */
    public void setLastName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.student.last_name</code>.
     */
    public String getLastName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.student.mac_id</code>.
     */
    public void setMacId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.student.mac_id</code>.
     */
    public String getMacId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.student.roll_number</code>.
     */
    public void setRollNumber(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.student.roll_number</code>.
     */
    public Integer getRollNumber() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.student.class_fid</code>.
     */
    public void setClassFid(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.student.class_fid</code>.
     */
    public Long getClassFid() {
        return (Long) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<UUID, String, String, String, Integer, Long> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<UUID, String, String, String, Integer, Long> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return Student.STUDENT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Student.STUDENT.FIRST_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Student.STUDENT.LAST_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Student.STUDENT.MAC_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Student.STUDENT.ROLL_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field6() {
        return Student.STUDENT.CLASS_FID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getFirstName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getLastName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getMacId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getRollNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component6() {
        return getClassFid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getFirstName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getLastName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getMacId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getRollNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value6() {
        return getClassFid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value1(UUID value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value2(String value) {
        setFirstName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value3(String value) {
        setLastName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value4(String value) {
        setMacId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value5(Integer value) {
        setRollNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord value6(Long value) {
        setClassFid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentRecord values(UUID value1, String value2, String value3, String value4, Integer value5, Long value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached StudentRecord
     */
    public StudentRecord() {
        super(Student.STUDENT);
    }

    /**
     * Create a detached, initialised StudentRecord
     */
    public StudentRecord(UUID id, String firstName, String lastName, String macId, Integer rollNumber, Long classFid) {
        super(Student.STUDENT);

        set(0, id);
        set(1, firstName);
        set(2, lastName);
        set(3, macId);
        set(4, rollNumber);
        set(5, classFid);
    }
}
