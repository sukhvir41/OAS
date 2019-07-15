/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author sukhvir
 */
@Entity(name = "Subject")
@Table(name = "subject")
public class Subject implements Serializable {

    @Id
    @GeneratedValue(generator = "subject_sequence")
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    @Size(max = 40, min = 2)
    @Getter
    @Setter
    @NonNull
    private String name;

    @Column(name = "elective", nullable = false)
    @Getter
    @Setter
    private boolean elective;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_fid", foreignKey = @ForeignKey(name = "subject_course_foreign_key"))
    @Getter
    @Setter
    private Course course;

    @OneToMany(mappedBy = "subject")
    @OrderBy("classRoom.name")
    @Getter
    @Setter
    private Set<SubjectClassRoomLink> classRooms = new HashSet<>();

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    @OrderBy("student.fName ASC, student.lName ASC")
    @Getter
    private Set<StudentSubjectLink> students = new HashSet<>();

    private Subject() {
    }

    public Subject(String name, boolean elective, Course course) {
        this.name = name;
        this.elective = elective;
        this.course = course;
    }


    /**
     * adds the subject to the course
     * OWNER of the relationship
     *
     * @param course
     */
    public void addCourse(Course course) {
        this.course = course;
    }

    /**
     * adds the the subject to the classroom
     * NOT the owner of the relationship
     *
     * @param classRoom
     */
    public void addClassRoom(ClassRoom classRoom) {
        this.classRooms.add(
                new SubjectClassRoomLink(this, classRoom)
        );
    }


    /**
     * this method adds the subject to the student
     * NOT the owner of the relationship
     *
     * @param student student to be added
     */
    public final void addStudent(Student student) {
        this.students.add(new StudentSubjectLink(student, this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id != null && Objects.equals(id, subject.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
