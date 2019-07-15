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
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sukhvir
 */
@Entity(name = "ClassRoom")
@Table(name = "class_room",
        uniqueConstraints = {
                @UniqueConstraint(name = "class_room_name", columnNames = "name")
        },
        indexes = {
                @Index(name = "class_room_name_index", columnList = "name")
        }
)
public class ClassRoom implements Serializable {

    @Id
    @GeneratedValue(generator = "class_room_sequence")
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

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

//    @OneToOne(mappedBy = "classRoom", fetch = FetchType.LAZY)
//    @Getter
//    @Setter
//    private Teacher classTeacher;

    @OneToMany(mappedBy = "classRoom", fetch = FetchType.LAZY)
    @OrderBy("fName ASC, LName ASC")
    @Getter
    @Setter
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "classRoom", fetch = FetchType.LAZY)
    @OrderBy("subject.name")
    @Getter
    @Setter
    private Set<SubjectClassRoomLink> subjects = new HashSet<>();

    private ClassRoom() {
    }


    public ClassRoom(@NonNull String name, @NonNull String division, int semester, int minimumSubjects) {
        this.name = name;
        this.division = division;
        this.semester = semester;
        this.minimumSubjects = minimumSubjects;
    }

    /**
     * this methods adds the teacher as class teacher to the class Room
     * NOT the owner of the relationship
     */
    final public void addClassTeacher(Teacher teacher) {
        //     this.classTeacher = teacher;
    }


    /**
     * this method adds the classroom to the course
     * OWNER of the relationship
     */
    final public void addCourse(Course course) {
        this.course = course;
    }

    /**
     * this method removes the classroom to the course
     * OWNER of the relationship
     */
    public void removeCourse() {
        this.course = null;
    }

    /**
     * this method adds the student to the classroom
     * NOT the owner of the relationship
     */
    public void addStudent(Student student) {
        this.students.remove(student);
        this.students.add(student);

    }

    /**
     * this method adds the subject to the classroom
     * NOT OWNER of the relationship
     *
     * @param subject subject to be added
     */
    public void addSubject(Subject subject) {
        //as it is a set no duplicates will exist
        this.subjects.add(
                new SubjectClassRoomLink(subject, this)
        );
    }

    /**
     * this method removes the subject to the classroom
     * OWNER of the relationship
     *
     * @param subject subject to be added
     */
    public void removeSubject(Subject subject) {
        this.subjects.remove(subject);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassRoom classRoom = (ClassRoom) o;
        return id != null && id == classRoom.id;
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
