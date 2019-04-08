/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author sukhvir
 */
@NamedEntityGraph(name = "getDepartment",
		attributeNodes = {
				@NamedAttributeNode("hod"),
				@NamedAttributeNode("courses"),
				@NamedAttributeNode("teachers")
		})
@Entity(name = "Department")
@Table(name = "department")
public class Department implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	@Getter
	@Setter
	private long id;

	@Column(name = "name", length = 40, nullable = false)
	@Getter
	@Setter
	@NonNull
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hod_teacher_fid", foreignKey = @ForeignKey(name = "department_hod_teacher_foreign_key"))
	@Getter
	@Setter
	private Teacher hod;

	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	@Getter
	@Setter
	private List<Course> courses = new ArrayList<>();

	@ManyToMany(mappedBy = "department", fetch = FetchType.LAZY)
	@Getter
	@Setter
	private Set<Teacher> teachers = new HashSet<>();

	public Department() {
	}

	public Department(String name) {
		this.name = name;
	}

	/**
	 * this methods adds the teacher to the department
	 *
	 * @param teacher teacher to be added to the department
	 */
	public void addTeacher(Teacher teacher) {
		if ( !teachers.contains( teacher ) ) {
			this.teachers.add( teacher );
			teacher.getDepartment().add( this );
		}
	}

	/**
	 * this method adds the course to the department and the department to the
	 * course
	 *
	 * @param course course to be added
	 */
	public void addCourse(Course course) {
		if ( !courses.contains( course ) ) {
			this.courses.add( course );
			course.setDepartment( this );
		}
	}

	/**
	 * this method adds the teacher as hod of department and vice versa
	 */
	public void addHod(Teacher teacher) {
		teacher.addHodOf( this );
	}

	@Override
	public String toString() {
		return name;
	}

	public static Department getDepartment(long id, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Department> query = builder.createQuery( Department.class );
		Root<Department> departmentRoot = query.from( Department.class );
		query.where(
				builder.equal( departmentRoot.get( Department_.id ), id )
		);

		return session.createQuery( query )
				//.setHint( Utils.LOAD_ENTITY_HINT, session.getEntityGraph( "getDepartment" ) )
				.applyLoadGraph( session.getEntityGraph( "getDepartment" ) )
				//.setMaxResults( 1 )
				.getSingleResult();
	}

}
