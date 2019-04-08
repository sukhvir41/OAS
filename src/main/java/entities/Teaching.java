/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author sukhvir
 */
@Entity(name = "Teaching")
@Table(name = "tcs", uniqueConstraints = @UniqueConstraint(name = "tcs_unique_constraint", columnNames = {
		"teacher_fid",
		"class_fid",
		"subject_fid"
}))
public class Teaching implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	@Getter
	@Setter
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_fid", foreignKey = @ForeignKey(name = "tcs_teacher_foreign_key"))
	@Getter
	@Setter
	private Teacher teacher;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_fid", foreignKey = @ForeignKey(name = "tcs_class_foreign_key"), nullable = false)
	@Getter
	@Setter
	@NonNull
	private ClassRoom classRoom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_fid", foreignKey = @ForeignKey(name = "tcs_subject_foreign_key"), nullable = false)
	@Getter
	@Setter
	@NonNull
	private Subject subject;

	@OneToMany(mappedBy = "teaching", fetch = FetchType.LAZY)
	@Getter
	@Setter
	List<Lecture> lectures = new ArrayList<>();

	public Teaching() {
	}

	public Teaching(ClassRoom classRoom, Subject subject) {
		this.classRoom = classRoom;
		this.subject = subject;
	}

	public Teaching(Teacher teacher, ClassRoom classRoom, Subject subject) {
		addTeacher( teacher );
		addClassRoom( classRoom );
		addSubject( subject );
	}

	/**
	 * this method adds lecture to teachings and vice versa
	 */
	public final void addLecture(Lecture lecture) {
		if ( !lectures.contains( lecture ) ) {
			lectures.add( lecture );
			lecture.setTeaching( this );
		}
	}

	/**
	 * this method adds class room to this teaching
	 */
	public final void addClassRoom(ClassRoom classRoom) {
		setClassRoom( classRoom );
	}

	/**
	 * this method adds subject to teaching
	 */
	public final void addSubject(Subject subject) {
		setSubject( subject );
	}

	/**
	 * this method adds teaching to teacher and vice versa
	 */
	public final void addTeacher(Teacher teacher) {
		teacher.addTeaching( this );
	}

	@Override
	public String toString() {
		return subject + " - " + classRoom;
	}

}
