/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author Kalpesh
 */

@NamedNativeQueries(
		@NamedNativeQuery(name = "getLectureId", query = "select get_lecture_id()")
)
@Entity(name = "Lecture")
@Table(name = "lecture")
public class Lecture implements Serializable {

	@Id
	@Column(name = "id")
	@GenericGenerator(name = "lectureIdGenerator",
			strategy = "entities.LectureIdGenerator")
	@GeneratedValue(generator = "lectureIdGenerator")
	@Getter
	@Setter
	private String id;

	@Column(name = "date", nullable = false)
	@Getter
	@Setter
	@Convert(converter = LocalDateTimeConverter.class)
	@NonNull
	private LocalDateTime date;

	@Column(name = "count", nullable = false)
	@Getter
	@Setter
	private int count = 1;

	@Column(name = "ended", nullable = false)
	@Getter
	@Setter
	private boolean ended = false;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tcs_fid", foreignKey = @ForeignKey(name = "lecture_tcs_foreign_key"))
	@Getter
	@Setter
	Teaching teaching;

	@OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
	@Getter
	private List<Attendance> attendances = new ArrayList<>();

	public Lecture() {
	}

	public Lecture(int count, Teaching teaching) {
		this.count = count;
		addTeaching( teaching );
		this.date = LocalDateTime.now();
	}

	public final void addTeaching(Teaching teaching) {
		teaching.addLecture( this );
	}

	@Override
	public String toString() {
		return getId() + " " + getTeaching();
	}


	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		Lecture lecture = (Lecture) o;
		return id.equals( lecture.id );
	}

	@Override
	public int hashCode() {
		return Objects.hash( id );
	}


}
