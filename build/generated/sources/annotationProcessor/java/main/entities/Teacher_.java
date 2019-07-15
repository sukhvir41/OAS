package entities;

import java.util.UUID;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Teacher.class)
public abstract class Teacher_ {

	public static volatile SingularAttribute<Teacher, String> lName;
	public static volatile ListAttribute<Teacher, Teaching> teaches;
	public static volatile SingularAttribute<Teacher, String> fName;
	public static volatile SingularAttribute<Teacher, Boolean> verified;
	public static volatile SingularAttribute<Teacher, ClassRoom> classRoom;
	public static volatile SetAttribute<Teacher, Department> hodOf;
	public static volatile SingularAttribute<Teacher, UUID> id;
	public static volatile SetAttribute<Teacher, TeacherDepartmentLink> departments;
	public static volatile SingularAttribute<Teacher, User> user;
	public static volatile SingularAttribute<Teacher, Boolean> unaccounted;

	public static final String L_NAME = "lName";
	public static final String TEACHES = "teaches";
	public static final String F_NAME = "fName";
	public static final String VERIFIED = "verified";
	public static final String CLASS_ROOM = "classRoom";
	public static final String HOD_OF = "hodOf";
	public static final String ID = "id";
	public static final String DEPARTMENTS = "departments";
	public static final String USER = "user";
	public static final String UNACCOUNTED = "unaccounted";

}

