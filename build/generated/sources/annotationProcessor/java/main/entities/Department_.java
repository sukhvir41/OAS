package entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ {

	public static volatile ListAttribute<Department, Course> courses;
	public static volatile SetAttribute<Department, TeacherDepartmentLink> teachers;
	public static volatile SingularAttribute<Department, String> name;
	public static volatile SingularAttribute<Department, Long> id;
	public static volatile SingularAttribute<Department, Teacher> hod;

	public static final String COURSES = "courses";
	public static final String TEACHERS = "teachers";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String HOD = "hod";

}

