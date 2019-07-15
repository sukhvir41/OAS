package entities;

import entities.Attendance.Id;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Attendance.class)
public abstract class Attendance_ {

	public static volatile SingularAttribute<Attendance, Boolean> attended;
	public static volatile SingularAttribute<Attendance, Student> student;
	public static volatile SingularAttribute<Attendance, Boolean> leave;
	public static volatile SingularAttribute<Attendance, Boolean> markedByTeacher;
	public static volatile SingularAttribute<Attendance, Lecture> lecture;
	public static volatile SingularAttribute<Attendance, Id> id;

	public static final String ATTENDED = "attended";
	public static final String STUDENT = "student";
	public static final String LEAVE = "leave";
	public static final String MARKED_BY_TEACHER = "markedByTeacher";
	public static final String LECTURE = "lecture";
	public static final String ID = "id";

}

