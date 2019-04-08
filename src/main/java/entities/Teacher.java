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
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author sukhvir
 */

@NamedEntityGraph(name = "userTeacher",
		attributeNodes = @NamedAttributeNode("user"))
@Entity(name = "Teacher")
@Table(name = "teacher")
//@PrimaryKeyJoinColumn(name = "id")
public class Teacher implements Serializable {

	@Id
	@Getter
	private UUID id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "id", nullable = false)
	@Getter
	private User user;

	@Column(name = "f_name", nullable = false, length = 40)
	@Getter
	@Setter
	private String fName;

	@Column(name = "l_name", nullable = false, length = 40)
	@Getter
	@Setter
	private String lName;

	@Column(name = "verified", nullable = false)
	@Getter
	@Setter
	private boolean verified;

	@Column(name = "unaccounted", nullable = false)
	@Getter
	@Setter
	private boolean unaccounted;

	@Column(name = "is_hod", nullable = false)
	@Getter
	@Setter
	private boolean hod;

	@OneToMany(mappedBy = "hod", fetch = FetchType.LAZY)
	@Getter
	@Setter
	private List<Department> hodOf = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_fid", foreignKey = @ForeignKey(name = "class_teacher_class_foreign_key"))
	@Getter
	@Setter
	private ClassRoom classRoom; // class teacher classroom

	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
	@Getter
	@Setter
	private List<Teaching> teaches = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "teacher_department_link",
			joinColumns = @JoinColumn(name = "teacher_fid"),
			inverseJoinColumns = @JoinColumn(name = "department_fid"),
			foreignKey = @ForeignKey(name = "teacher_department_link_teacher_foreign_key"),
			inverseForeignKey = @ForeignKey(name = "teacher_department_link_department_foreign_key"))
	@Getter
	@Setter
	private Set<Department> department = new HashSet<>();

	public Teacher() {
	}

	public Teacher(String fName, String lName, String username, String password, String email, long number) {
		//super(username, password, email, number);
		this.user = new User( username, password, email, number, UserType.Teacher );
		this.fName = fName;
		this.lName = lName;
		setVerified( false );

	}

	public void unaccount() {
		if ( !verified ) {
			unaccounted = true;
		}
	}

	/**
	 * this method adds class room to class teacher and vice versa
	 */
	public final void addClassRoom(ClassRoom classRoom) {
		classRoom.addClassTeacher( this );
	}

	/**
	 * this methods adds the teaching to teacher and vice versa
	 */
	public final void addTeaching(Teaching teaching) {
		if ( !teaches.contains( teaching ) ) {
			this.teaches.add( teaching );
			teaching.setTeacher( this );
		}
	}

	/**
	 * this methods adds the teacher to the department and the department to the
	 * teacher
	 *
	 * @param department
	 */
	public void addDepartment(Department department) {
		department.addTeacher( this );
	}

	/**
	 * this method adds department hod to teacher and vice versa but does not
	 * mark hod to true
	 */
	public void addHodOf(Department department) {
		if ( !hodOf.contains( department ) ) {
			hodOf.add( department );
			department.setHod( this );
			hod = true;
		}
	}

	@Override
	public String toString() {
		return fName + " " + lName;
	}
}
