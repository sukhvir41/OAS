/*
 * This file is generated by jOOQ.
 */
package jooq.entities.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import jooq.entities.Indexes;
import jooq.entities.Keys;
import jooq.entities.Public;
import jooq.entities.tables.records.SubjectClassLinkRecord;

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
        "jOOQ version:3.11.12"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SubjectClassLink extends TableImpl<SubjectClassLinkRecord> {

    private static final long serialVersionUID = -779950492;

    /**
     * The reference instance of <code>public.subject_class_link</code>
     */
    public static final SubjectClassLink SUBJECT_CLASS_LINK = new SubjectClassLink();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SubjectClassLinkRecord> getRecordType() {
        return SubjectClassLinkRecord.class;
    }

    /**
     * The column <code>public.subject_class_link.class_room_fid</code>.
     */
    public final TableField<SubjectClassLinkRecord, Long> CLASS_ROOM_FID = createField("class_room_fid", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.subject_class_link.subject_fid</code>.
     */
    public final TableField<SubjectClassLinkRecord, Long> SUBJECT_FID = createField("subject_fid", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>public.subject_class_link</code> table reference
     */
    public SubjectClassLink() {
        this(DSL.name("subject_class_link"), null);
    }

    /**
     * Create an aliased <code>public.subject_class_link</code> table reference
     */
    public SubjectClassLink(String alias) {
        this(DSL.name(alias), SUBJECT_CLASS_LINK);
    }

    /**
     * Create an aliased <code>public.subject_class_link</code> table reference
     */
    public SubjectClassLink(Name alias) {
        this(alias, SUBJECT_CLASS_LINK);
    }

    private SubjectClassLink(Name alias, Table<SubjectClassLinkRecord> aliased) {
        this(alias, aliased, null);
    }

    private SubjectClassLink(Name alias, Table<SubjectClassLinkRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> SubjectClassLink(Table<O> child, ForeignKey<O, SubjectClassLinkRecord> key) {
        super(child, key, SUBJECT_CLASS_LINK);
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
        return Arrays.<Index>asList(Indexes.SUBJECT_CLASS_LINK_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SubjectClassLinkRecord> getPrimaryKey() {
        return Keys.SUBJECT_CLASS_LINK_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SubjectClassLinkRecord>> getKeys() {
        return Arrays.<UniqueKey<SubjectClassLinkRecord>>asList(Keys.SUBJECT_CLASS_LINK_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubjectClassLink as(String alias) {
        return new SubjectClassLink(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubjectClassLink as(Name alias) {
        return new SubjectClassLink(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SubjectClassLink rename(String name) {
        return new SubjectClassLink(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SubjectClassLink rename(Name name) {
        return new SubjectClassLink(name, null);
    }
}
