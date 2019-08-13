package entities;

import entities.TeacherDepartmentLink.Id;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TeacherDepartmentLink.class)
public abstract class TeacherDepartmentLink_ {

	public static volatile SingularAttribute<TeacherDepartmentLink, Teacher> teacher;
	public static volatile SingularAttribute<TeacherDepartmentLink, Id> id;
	public static volatile SingularAttribute<TeacherDepartmentLink, Department> department;

	public static final String TEACHER = "teacher";
	public static final String ID = "id";
	public static final String DEPARTMENT = "department";

}

