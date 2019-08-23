package utility;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.JulianFields;
import java.time.temporal.TemporalField;
import java.util.*;

import static jooq.entities.Tables.*;


public class testing1 {


    public static void main(String[] args) {

        var dsl = Utils.getDsl();
        var nativeQueryString = dsl
                .delete(CLASS_TEACHER_CLASS_ROOM_LINK)
                .where(CLASS_TEACHER_CLASS_ROOM_LINK.CLASS_TEACHER_FID.in(
                        dsl.select(TEACHER_DEPARTMENT_LINK.TEACHER_FID)
                                .from(TEACHER_DEPARTMENT_LINK)
                                .innerJoin(CLASS_TEACHER_CLASS_ROOM_LINK)
                                .on(CLASS_TEACHER_CLASS_ROOM_LINK.CLASS_TEACHER_FID.eq(TEACHER_DEPARTMENT_LINK.TEACHER_FID))
                                .innerJoin(CLASS_ROOM)
                                .on(CLASS_ROOM.ID.eq(CLASS_TEACHER_CLASS_ROOM_LINK.CLASS_ROOM_FID).and(CLASS_ROOM.COURSE_FID.eq(1l)))
                                .where(TEACHER_DEPARTMENT_LINK.DEPARTMENT_FID.eq(1l))
                )).getSQL();

        System.out.println(
                nativeQueryString
        );


    }


}


