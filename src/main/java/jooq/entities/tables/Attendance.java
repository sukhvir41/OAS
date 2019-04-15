/*
 * This file is generated by jOOQ.
 */
package jooq.entities.tables;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import jooq.entities.Indexes;
import jooq.entities.Keys;
import jooq.entities.Public;
import jooq.entities.tables.records.AttendanceRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class Attendance extends TableImpl<AttendanceRecord> {

    private static final long serialVersionUID = -353960525;

    /**
     * The reference instance of <code>public.attendance</code>
     */
    public static final Attendance ATTENDANCE = new Attendance();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AttendanceRecord> getRecordType() {
        return AttendanceRecord.class;
    }

    /**
     * The column <code>public.attendance.lecture_fid</code>.
     */
    public final TableField<AttendanceRecord, String> LECTURE_FID = createField("lecture_fid", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.attendance.student_fid</code>.
     */
    public final TableField<AttendanceRecord, UUID> STUDENT_FID = createField("student_fid", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.attendance.attended</code>.
     */
    public final TableField<AttendanceRecord, Boolean> ATTENDED = createField("attended", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.attendance.leave</code>.
     */
    public final TableField<AttendanceRecord, Boolean> LEAVE = createField("leave", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.attendance.marked_by_teacher</code>.
     */
    public final TableField<AttendanceRecord, Boolean> MARKED_BY_TEACHER = createField("marked_by_teacher", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * Create a <code>public.attendance</code> table reference
     */
    public Attendance() {
        this(DSL.name("attendance"), null);
    }

    /**
     * Create an aliased <code>public.attendance</code> table reference
     */
    public Attendance(String alias) {
        this(DSL.name(alias), ATTENDANCE);
    }

    /**
     * Create an aliased <code>public.attendance</code> table reference
     */
    public Attendance(Name alias) {
        this(alias, ATTENDANCE);
    }

    private Attendance(Name alias, Table<AttendanceRecord> aliased) {
        this(alias, aliased, null);
    }

    private Attendance(Name alias, Table<AttendanceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Attendance(Table<O> child, ForeignKey<O, AttendanceRecord> key) {
        super(child, key, ATTENDANCE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ATTENDANCE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<AttendanceRecord> getPrimaryKey() {
        return Keys.ATTENDANCE_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AttendanceRecord>> getKeys() {
        return Arrays.<UniqueKey<AttendanceRecord>>asList(Keys.ATTENDANCE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<AttendanceRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AttendanceRecord, ?>>asList(Keys.ATTENDANCE__LECTURE_FOREIGN_KEY, Keys.ATTENDANCE__STUDENT_FOREIGN_KEY);
    }

    public Lecture lecture() {
        return new Lecture(this, Keys.ATTENDANCE__LECTURE_FOREIGN_KEY);
    }

    public Student student() {
        return new Student(this, Keys.ATTENDANCE__STUDENT_FOREIGN_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Attendance as(String alias) {
        return new Attendance(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Attendance as(Name alias) {
        return new Attendance(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Attendance rename(String name) {
        return new Attendance(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Attendance rename(Name name) {
        return new Attendance(name, null);
    }
}
