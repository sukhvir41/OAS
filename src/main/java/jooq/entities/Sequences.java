/*
 * This file is generated by jOOQ.
 */
package jooq.entities;


import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;

import javax.annotation.Generated;


/**
 * Convenience access to all sequences in public
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.11.12"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Sequences {

    /**
     * The sequence <code>public.class_room_sequence</code>
     */
    public static final Sequence<Long> CLASS_ROOM_SEQUENCE = new SequenceImpl<Long>("class_room_sequence", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.course_sequence</code>
     */
    public static final Sequence<Long> COURSE_SEQUENCE = new SequenceImpl<Long>("course_sequence", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.department_sequence</code>
     */
    public static final Sequence<Long> DEPARTMENT_SEQUENCE = new SequenceImpl<Long>("department_sequence", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.subject_sequence</code>
     */
    public static final Sequence<Long> SUBJECT_SEQUENCE = new SequenceImpl<Long>("subject_sequence", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));
}
