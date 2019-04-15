package entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ClassRoom.class)
public abstract class ClassRoom_ {

	public static volatile SingularAttribute<ClassRoom, String> division;
	public static volatile SetAttribute<ClassRoom, Subject> subjects;
	public static volatile SingularAttribute<ClassRoom, String> name;
	public static volatile SingularAttribute<ClassRoom, Integer> minimumSubjects;
	public static volatile SingularAttribute<ClassRoom, Course> course;
	public static volatile ListAttribute<ClassRoom, Student> students;
	public static volatile SingularAttribute<ClassRoom, Integer> semester;
	public static volatile SingularAttribute<ClassRoom, Long> id;
	public static volatile SingularAttribute<ClassRoom, Teacher> classTeacher;

	public static final String DIVISION = "division";
	public static final String SUBJECTS = "subjects";
	public static final String NAME = "name";
	public static final String MINIMUM_SUBJECTS = "minimumSubjects";
	public static final String COURSE = "course";
	public static final String STUDENTS = "students";
	public static final String SEMESTER = "semester";
	public static final String ID = "id";
	public static final String CLASS_TEACHER = "classTeacher";

}

