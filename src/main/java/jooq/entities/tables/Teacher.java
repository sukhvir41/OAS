/*
 * This file is generated by jOOQ.
 */
package jooq.entities.tables;


import jooq.entities.Indexes;
import jooq.entities.Keys;
import jooq.entities.Public;
import jooq.entities.tables.records.TeacherRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


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
public class Teacher extends TableImpl<TeacherRecord> {

    private static final long serialVersionUID = 1883245895;

    /**
     * The reference instance of <code>public.teacher</code>
     */
    public static final Teacher TEACHER = new Teacher();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TeacherRecord> getRecordType() {
        return TeacherRecord.class;
    }

    /**
     * The column <code>public.teacher.id</code>.
     */
    public final TableField<TeacherRecord, UUID> ID = createField("id", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.teacher.f_name</code>.
     */
    public final TableField<TeacherRecord, String> F_NAME = createField("f_name", org.jooq.impl.SQLDataType.VARCHAR(40).nullable(false), this, "");

    /**
     * The column <code>public.teacher.is_hod</code>.
     */
    public final TableField<TeacherRecord, Boolean> IS_HOD = createField("is_hod", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.teacher.l_name</code>.
     */
    public final TableField<TeacherRecord, String> L_NAME = createField("l_name", org.jooq.impl.SQLDataType.VARCHAR(40).nullable(false), this, "");

    /**
     * The column <code>public.teacher.unaccounted</code>.
     */
    public final TableField<TeacherRecord, Boolean> UNACCOUNTED = createField("unaccounted", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.teacher.verified</code>.
     */
    public final TableField<TeacherRecord, Boolean> VERIFIED = createField("verified", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.teacher.class_fid</code>.
     */
    public final TableField<TeacherRecord, Long> CLASS_FID = createField("class_fid", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * Create a <code>public.teacher</code> table reference
     */
    public Teacher() {
        this(DSL.name("teacher"), null);
    }

    /**
     * Create an aliased <code>public.teacher</code> table reference
     */
    public Teacher(String alias) {
        this(DSL.name(alias), TEACHER);
    }

    /**
     * Create an aliased <code>public.teacher</code> table reference
     */
    public Teacher(Name alias) {
        this(alias, TEACHER);
    }

    private Teacher(Name alias, Table<TeacherRecord> aliased) {
        this(alias, aliased, null);
    }

    private Teacher(Name alias, Table<TeacherRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Teacher(Table<O> child, ForeignKey<O, TeacherRecord> key) {
        super(child, key, TEACHER);
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
        return Arrays.<Index>asList(Indexes.TEACHER_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TeacherRecord> getPrimaryKey() {
        return Keys.TEACHER_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TeacherRecord>> getKeys() {
        return Arrays.<UniqueKey<TeacherRecord>>asList(Keys.TEACHER_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TeacherRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TeacherRecord, ?>>asList(Keys.TEACHER__FKSPQY2S83GVL6G8NT5AUBLS90K, Keys.TEACHER__CLASS_TEACHER_CLASS_FOREIGN_KEY);
    }

    public Users users() {
        return new Users(this, Keys.TEACHER__FKSPQY2S83GVL6G8NT5AUBLS90K);
    }

    public ClassRoom classRoom() {
        return new ClassRoom(this, Keys.TEACHER__CLASS_TEACHER_CLASS_FOREIGN_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Teacher as(String alias) {
        return new Teacher(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Teacher as(Name alias) {
        return new Teacher(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Teacher rename(String name) {
        return new Teacher(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Teacher rename(Name name) {
        return new Teacher(name, null);
    }
}
