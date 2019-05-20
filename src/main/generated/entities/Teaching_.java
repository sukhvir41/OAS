package entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Teaching.class)
public abstract class Teaching_ {

	public static volatile SingularAttribute<Teaching, Teacher> teacher;
	public static volatile SingularAttribute<Teaching, Subject> subject;
	public static volatile SingularAttribute<Teaching, ClassRoom> classRoom;
	public static volatile ListAttribute<Teaching, Lecture> lectures;
	public static volatile SingularAttribute<Teaching, Long> id;

	public static final String TEACHER = "teacher";
	public static final String SUBJECT = "subject";
	public static final String CLASS_ROOM = "classRoom";
	public static final String LECTURES = "lectures";
	public static final String ID = "id";

}

