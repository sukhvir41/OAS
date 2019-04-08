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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author sukhvir
 */
@Entity(name = "ClassRoom")
@Table(name = "class")
public class ClassRoom implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	@Getter
	@Setter
	private long id;

	@Column(name = "name", nullable = false, length = 40)
	@Getter
	@Setter
	@NonNull
	private String name;

	@Column(name = "division", length = 2, nullable = false)
	@Getter
	@Setter
	@NonNull
	private String division;

	@Column(name = "semester", nullable = false)
	@Getter
	@Setter
	private int semester;

	// do we need this
	@Column(name = "minimum_subjects", nullable = false)
	@Getter
	@Setter
	private int minimumSubjects;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_fid", foreignKey = @ForeignKey(name = "class_course_foreign_key"))
	@Getter
	@Setter
	private Course course;

	@OneToOne(mappedBy = "classRoom", fetch = FetchType.LAZY)
	@Getter
	@Setter
	private Teacher classTeacher;

	@OneToMany(mappedBy = "classRoom", fetch = FetchType.LAZY)
	@Getter
	@Setter
	private List<Student> students = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "subject_class_link",
			joinColumns = @JoinColumn(name = "class_fid"),
			inverseJoinColumns = @JoinColumn(name = "subject_fid"),
			foreignKey = @ForeignKey(name = "subject_class_link_class_foreign_key"),
			inverseForeignKey = @ForeignKey(name = "subject_class_link_subject_foreign_key"))
	@Getter
	@Setter
	private Set<Subject> subjects = new HashSet<>();

	public ClassRoom() {
	}

	public ClassRoom(String name, String division, int semester, int minimumSubjects) {
		this.name = name;
		this.division = division;
		this.semester = semester;
		this.minimumSubjects = minimumSubjects;
	}

	public ClassRoom(String name, String division, int semester, Course course) {
		this.name = name;
		this.division = division;
		this.semester = semester;
		this.course = course;
	}

	public ClassRoom(String name, String division, int semester, Course course, int minimumSubjects) {
		this.name = name;
		this.division = division;
		this.semester = semester;
		this.minimumSubjects = minimumSubjects;
		addCourse( course );
	}

	/**
	 * this methods adds the teacher as class teacher to the class Room and vice
	 * versa
	 */
	final public void addClassTeacher(Teacher teacher) {
		teacher.setClassRoom( this );
		this.classTeacher = teacher;
	}

	/**
	 * this method adds the classroom to the course and the course to the
	 * classroom
	 */
	final public void addCourse(Course course) {
		course.addClassRoom( this );
	}

	/**
	 * this method adds the student to the classroom and the class room to the
	 * student
	 */
	public void addStudent(Student student) {
		this.students.add( student );
		student.setClassRoom( this );
	}

	/**
	 * this method adds the subject to the classroom
	 *
	 * @param subject subject to be added
	 */
	public void addSubject(Subject subject) {
		this.subjects.add( subject );
		subject.getClassRooms().add( this );
	}

	@Override
	public String toString() {
		return name + " - " + division + " - " + course.toString();
	}


}
