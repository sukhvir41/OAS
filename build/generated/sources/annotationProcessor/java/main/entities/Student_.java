package entities;

import java.util.UUID;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Student.class)
public abstract class Student_ {

	public static volatile SingularAttribute<Student, String> lName;
	public static volatile SingularAttribute<Student, String> fName;
	public static volatile SingularAttribute<Student, String> macId;
	public static volatile SetAttribute<Student, StudentSubjectLink> subjects;
	public static volatile SingularAttribute<Student, Integer> rollNumber;
	public static volatile SingularAttribute<Student, ClassRoom> classRoom;
	public static volatile SingularAttribute<Student, UUID> id;
	public static volatile SingularAttribute<Student, User> user;
	public static volatile ListAttribute<Student, Attendance> attendances;

	public static final String L_NAME = "lName";
	public static final String F_NAME = "fName";
	public static final String MAC_ID = "macId";
	public static final String SUBJECTS = "subjects";
	public static final String ROLL_NUMBER = "rollNumber";
	public static final String CLASS_ROOM = "classRoom";
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String ATTENDANCES = "attendances";

}

