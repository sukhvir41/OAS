package entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Course.class)
public abstract class Course_ {

	public static volatile ListAttribute<Course, Subject> subjects;
	public static volatile SingularAttribute<Course, String> name;
	public static volatile ListAttribute<Course, ClassRoom> classRooms;
	public static volatile SingularAttribute<Course, Long> id;
	public static volatile SingularAttribute<Course, Department> department;

	public static final String SUBJECTS = "subjects";
	public static final String NAME = "name";
	public static final String CLASS_ROOMS = "classRooms";
	public static final String ID = "id";
	public static final String DEPARTMENT = "department";

}

