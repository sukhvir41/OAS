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
import jooq.entities.tables.records.AdminRecord;

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
public class Admin extends TableImpl<AdminRecord> {

    private static final long serialVersionUID = -2080380111;

    /**
     * The reference instance of <code>public.admin</code>
     */
    public static final Admin ADMIN = new Admin();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AdminRecord> getRecordType() {
        return AdminRecord.class;
    }

    /**
     * The column <code>public.admin.id</code>.
     */
    public final TableField<AdminRecord, UUID> ID = createField("id", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.admin.type</code>.
     */
    public final TableField<AdminRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * Create a <code>public.admin</code> table reference
     */
    public Admin() {
        this(DSL.name("admin"), null);
    }

    /**
     * Create an aliased <code>public.admin</code> table reference
     */
    public Admin(String alias) {
        this(DSL.name(alias), ADMIN);
    }

    /**
     * Create an aliased <code>public.admin</code> table reference
     */
    public Admin(Name alias) {
        this(alias, ADMIN);
    }

    private Admin(Name alias, Table<AdminRecord> aliased) {
        this(alias, aliased, null);
    }

    private Admin(Name alias, Table<AdminRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Admin(Table<O> child, ForeignKey<O, AdminRecord> key) {
        super(child, key, ADMIN);
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
        return Arrays.<Index>asList(Indexes.ADMIN_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<AdminRecord> getPrimaryKey() {
        return Keys.ADMIN_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AdminRecord>> getKeys() {
        return Arrays.<UniqueKey<AdminRecord>>asList(Keys.ADMIN_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<AdminRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AdminRecord, ?>>asList(Keys.ADMIN__USER_FOREIGN_KEY);
    }

    public Users users() {
        return new Users(this, Keys.ADMIN__USER_FOREIGN_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Admin as(String alias) {
        return new Admin(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Admin as(Name alias) {
        return new Admin(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Admin rename(String name) {
        return new Admin(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Admin rename(Name name) {
        return new Admin(name, null);
    }
}
