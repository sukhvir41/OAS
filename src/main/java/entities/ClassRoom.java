/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sukhvir
 */
@Entity(name = "ClassRoom")
@Table(name = "class_room")
public class ClassRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_fid", foreignKey = @ForeignKey(name = "class_course_foreign_key"), nullable = false)
    @Getter
    @Setter
    private Course course;

    @OneToOne(mappedBy = "classRoom", fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @Getter
    @Setter
    private Teacher classTeacher;

    @OneToMany(mappedBy = "classRoom", fetch = FetchType.LAZY)
    @OrderBy("fName ASC, LName ASC")
    @Getter
    @Setter
    private List<Student> students = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("name")
    @JoinTable(name = "subject_class_link",
            joinColumns = @JoinColumn(name = "class_fid"),
            inverseJoinColumns = @JoinColumn(name = "subject_fid"),
            foreignKey = @ForeignKey(name = "subject_class_link_class_foreign_key"),
            inverseForeignKey = @ForeignKey(name = "subject_class_link_subject_foreign_key"))
    @Getter
    @Setter
    private Set<Subject> subjects = new HashSet<>();

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
        this.classTeacher = teacher;
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
     * OWNER of the relationship
     *
     * @param subject subject to be added
     */
    public void addSubject(Subject subject) {
        //as it is a set no duplicates will exist
        this.subjects.add(subject);
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
