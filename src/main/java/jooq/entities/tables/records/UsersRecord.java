/*
 * This file is generated by jOOQ.
 */
package jooq.entities.tables.records;


import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Generated;

import jooq.entities.tables.Users;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
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
public class UsersRecord extends UpdatableRecordImpl<UsersRecord> implements Record11<UUID, Timestamp, String, Long, String, String, String, String, Boolean, String, String> {

    private static final long serialVersionUID = 2074839628;

    /**
     * Setter for <code>public.users.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.users.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.users.date</code>.
     */
    public void setDate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.users.date</code>.
     */
    public Timestamp getDate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>public.users.email</code>.
     */
    public void setEmail(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.users.email</code>.
     */
    public String getEmail() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.users.number</code>.
     */
    public void setNumber(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.users.number</code>.
     */
    public Long getNumber() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>public.users.password</code>.
     */
    public void setPassword(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.users.password</code>.
     */
    public String getPassword() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.users.session_id</code>.
     */
    public void setSessionId(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.users.session_id</code>.
     */
    public String getSessionId() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.users.session_token</code>.
     */
    public void setSessionToken(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.users.session_token</code>.
     */
    public String getSessionToken() {
        return (String) get(6);
    }

    /**
     * Setter for <code>public.users.token</code>.
     */
    public void setToken(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.users.token</code>.
     */
    public String getToken() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.users.used</code>.
     */
    public void setUsed(Boolean value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.users.used</code>.
     */
    public Boolean getUsed() {
        return (Boolean) get(8);
    }

    /**
     * Setter for <code>public.users.user_type</code>.
     */
    public void setUserType(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.users.user_type</code>.
     */
    public String getUserType() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.users.username</code>.
     */
    public void setUsername(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.users.username</code>.
     */
    public String getUsername() {
        return (String) get(10);
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
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<UUID, Timestamp, String, Long, String, String, String, String, Boolean, String, String> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<UUID, Timestamp, String, Long, String, String, String, String, Boolean, String, String> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return Users.USERS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return Users.USERS.DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Users.USERS.EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field4() {
        return Users.USERS.NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Users.USERS.PASSWORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Users.USERS.SESSION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Users.USERS.SESSION_TOKEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return Users.USERS.TOKEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field9() {
        return Users.USERS.USED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return Users.USERS.USER_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return Users.USERS.USERNAME;
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
    public Timestamp component2() {
        return getDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component4() {
        return getNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getSessionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getSessionToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component9() {
        return getUsed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getUserType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getUsername();
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
    public Timestamp value2() {
        return getDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value4() {
        return getNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getSessionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getSessionToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value9() {
        return getUsed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getUserType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsersRecord value1(UUID value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsersRecord value2(Timestamp value) {
        setDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsersRecord value3(String value) {
        setEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsersRecord value4(Long value) {
        setNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsersRecord value5(String value) {
        setPassword(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsersRecord value6(String value) {
        setSessionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsersRecord value7(String value) {
        setSessionToken(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsersRecord value8(String value) {
        setToken(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsersRecord value9(Boolean value) {
        setUsed(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsersRecord value10(String value) {
        setUserType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsersRecord value11(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsersRecord values(UUID value1, Timestamp value2, String value3, Long value4, String value5, String value6, String value7, String value8, Boolean value9, String value10, String value11) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UsersRecord
     */
    public UsersRecord() {
        super(Users.USERS);
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    public UsersRecord(UUID id, Timestamp date, String email, Long number, String password, String sessionId, String sessionToken, String token, Boolean used, String userType, String username) {
        super(Users.USERS);

        set(0, id);
        set(1, date);
        set(2, email);
        set(3, number);
        set(4, password);
        set(5, sessionId);
        set(6, sessionToken);
        set(7, token);
        set(8, used);
        set(9, userType);
        set(10, username);
    }
}
