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
import jooq.entities.tables.records.StudentRecord;

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
public class Student extends TableImpl<StudentRecord> {

    private static final long serialVersionUID = -1937554005;

    /**
     * The reference instance of <code>public.student</code>
     */
    public static final Student STUDENT = new Student();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StudentRecord> getRecordType() {
        return StudentRecord.class;
    }

    /**
     * The column <code>public.student.id</code>.
     */
    public final TableField<StudentRecord, UUID> ID = createField("id", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.student.first_name</code>.
     */
    public final TableField<StudentRecord, String> FIRST_NAME = createField("first_name", org.jooq.impl.SQLDataType.VARCHAR(40).nullable(false), this, "");

    /**
     * The column <code>public.student.last_name</code>.
     */
    public final TableField<StudentRecord, String> LAST_NAME = createField("last_name", org.jooq.impl.SQLDataType.VARCHAR(40).nullable(false), this, "");

    /**
     * The column <code>public.student.mac_id</code>.
     */
    public final TableField<StudentRecord, String> MAC_ID = createField("mac_id", org.jooq.impl.SQLDataType.VARCHAR(17), this, "");

    /**
     * The column <code>public.student.roll_number</code>.
     */
    public final TableField<StudentRecord, Integer> ROLL_NUMBER = createField("roll_number", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.student.unaccounted</code>.
     */
    public final TableField<StudentRecord, Boolean> UNACCOUNTED = createField("unaccounted", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

    /**
     * The column <code>public.student.verified</code>.
     */
    public final TableField<StudentRecord, Boolean> VERIFIED = createField("verified", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.student.class_fid</code>.
     */
    public final TableField<StudentRecord, Long> CLASS_FID = createField("class_fid", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * Create a <code>public.student</code> table reference
     */
    public Student() {
        this(DSL.name("student"), null);
    }

    /**
     * Create an aliased <code>public.student</code> table reference
     */
    public Student(String alias) {
        this(DSL.name(alias), STUDENT);
    }

    /**
     * Create an aliased <code>public.student</code> table reference
     */
    public Student(Name alias) {
        this(alias, STUDENT);
    }

    private Student(Name alias, Table<StudentRecord> aliased) {
        this(alias, aliased, null);
    }

    private Student(Name alias, Table<StudentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Student(Table<O> child, ForeignKey<O, StudentRecord> key) {
        super(child, key, STUDENT);
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
        return Arrays.<Index>asList(Indexes.STUDENT_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<StudentRecord> getPrimaryKey() {
        return Keys.STUDENT_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StudentRecord>> getKeys() {
        return Arrays.<UniqueKey<StudentRecord>>asList(Keys.STUDENT_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<StudentRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<StudentRecord, ?>>asList(Keys.STUDENT__FK3W7XMT19I6A0CR7KP8C80EK40, Keys.STUDENT__STUDENT_FOREIGN_KEY);
    }

    public Users users() {
        return new Users(this, Keys.STUDENT__FK3W7XMT19I6A0CR7KP8C80EK40);
    }

    public ClassRoom classRoom() {
        return new ClassRoom(this, Keys.STUDENT__STUDENT_FOREIGN_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Student as(String alias) {
        return new Student(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Student as(Name alias) {
        return new Student(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Student rename(String name) {
        return new Student(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Student rename(Name name) {
        return new Student(name, null);
    }
}
