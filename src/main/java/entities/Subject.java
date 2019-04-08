/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.HashSet;
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
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author sukhvir
 */
@Entity(name = "Subject")
@Table(name = "subject")
public class Subject implements Serializable {

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

	@Column(name = "elective", nullable = false)
	@Getter
	@Setter
	private boolean elective;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_fid", foreignKey = @ForeignKey(name = "subject_course_foreign_key"))
	@Getter
	@Setter
	private Course course;

	@ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
	@Getter
	@Setter
	private Set<ClassRoom> classRooms = new HashSet<>();

	@ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
	@Getter
	@Setter
	private Set<Student> students = new HashSet<>();

	public Subject() {
	}

	public Subject(String name, boolean elective) {
		this.name = name;
		this.elective = elective;
	}

	public Subject(String name, boolean elective, Course course) {
		this.name = name;
		this.elective = elective;
		addCourse( course );
	}

	/**
	 * this method adds the subject to the student and the student to the
	 * subject to the subject
	 *
	 * @param student student to be added
	 */
	public final void addStudent(Student student) {
		student.addSubject( this );
	}

	/**
	 * this method add the subject to the course and the course to the subject
	 *
	 * @param course course to be added
	 */
	final public void addCourse(Course course) {
		course.addSubject( this );
	}

	/**
	 * this method adds subject to class room and vice versa
	 */
	public final void addClassRoom(ClassRoom classRoom) {
		classRoom.addSubject( this );
	}

	@Override
	public String toString() {
		return name;
	}

}
