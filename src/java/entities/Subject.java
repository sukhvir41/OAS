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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "subject")
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_id")
    @Getter
    @Setter
    private int id;

    @Column(name = "sub_name")
    @Getter
    @Setter
    private String name;

    @Column(name = "elective")
    @Getter
    @Setter
    private boolean elective;

    @ManyToOne
    @JoinTable(name = "course_subject_link", joinColumns = @JoinColumn(name = "subject_fid"), inverseJoinColumns = @JoinColumn(name = "course_fid"))
    @Getter
    @Setter
    private Course course;

    @ManyToMany(mappedBy = "subjects")
    @BatchSize(size = 20)
    @Getter
    @Setter
    private List<ClassRoom> classRooms = new ArrayList<>();

    @ManyToMany(mappedBy = "subjects")
    @BatchSize(size = 40)
    @Getter
    @Setter
    List<Student> students = new ArrayList<>();

    public Subject() {
    }

    public Subject(String name, boolean elective) {
        this.name = name;
        this.elective = elective;
    }

    public Subject(String name, boolean elective, Course course) {
        this.name = name;
        this.elective = elective;
        addCourse(course);
    }

    /**
     * this method adds the subject to the student and the student to the
     * subject to the subject
     *
     * @param student student to be added
     */
    public final void addStudent(Student student) {
        student.addSubject(this);
    }

    /**
     * this method add the subject to the course and the course to the subject
     *
     * @param course course to be added
     */
    final public void addCourse(Course course) {
        course.addSubject(this);
    }

    /**
     * this method adds subject to class room and vice versa
     */
    public final void addClassRoom(ClassRoom classRoom) {
        classRoom.addSubject(this);
    }

    @Override
    public String toString() {
        return name;
    }

}
