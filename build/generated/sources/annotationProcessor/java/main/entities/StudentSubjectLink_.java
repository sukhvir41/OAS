package entities;

import entities.StudentSubjectLink.Id;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StudentSubjectLink.class)
public abstract class StudentSubjectLink_ {

	public static volatile SingularAttribute<StudentSubjectLink, Student> student;
	public static volatile SingularAttribute<StudentSubjectLink, Subject> subject;
	public static volatile SingularAttribute<StudentSubjectLink, Id> id;

	public static final String STUDENT = "student";
	public static final String SUBJECT = "subject";
	public static final String ID = "id";

}

