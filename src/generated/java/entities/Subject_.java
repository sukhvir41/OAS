package entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Subject.class)
public abstract class Subject_ {

	public static volatile SingularAttribute<Subject, Boolean> elective;
	public static volatile SingularAttribute<Subject, String> name;
	public static volatile SingularAttribute<Subject, Course> course;
	public static volatile SetAttribute<Subject, ClassRoom> classRooms;
	public static volatile SetAttribute<Subject, Student> students;
	public static volatile SingularAttribute<Subject, Long> id;

	public static final String ELECTIVE = "elective";
	public static final String NAME = "name";
	public static final String COURSE = "course";
	public static final String CLASS_ROOMS = "classRooms";
	public static final String STUDENTS = "students";
	public static final String ID = "id";

}

