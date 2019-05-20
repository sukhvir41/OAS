package entities;

import java.util.UUID;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AttendanceId.class)
public abstract class AttendanceId_ {

	public static volatile SingularAttribute<AttendanceId, UUID> studentId;
	public static volatile SingularAttribute<AttendanceId, String> lectureId;

	public static final String STUDENT_ID = "studentId";
	public static final String LECTURE_ID = "lectureId";

}

