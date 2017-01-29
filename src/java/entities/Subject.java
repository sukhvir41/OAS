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
    private int id;

    @Column(name = "sub_name")
    private String name;

    @Column(name = "elective")
    private boolean elective;

    @ManyToOne
    @JoinTable(name = "course_subject_link", joinColumns = @JoinColumn(name = "subject_fid"), inverseJoinColumns = @JoinColumn(name = "course_fid"))
    private Course course;

    @ManyToMany(mappedBy = "subjects")
    private List<ClassRoom> classRooms = new ArrayList<>();

    @ManyToMany(mappedBy = "subjects")
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
    public void addStudent(Student student) {
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

    public void addClassRoom(ClassRoom classRoom) {
        classRoom.addSubject(this);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isElective() {
        return elective;
    }

    public void setElective(boolean elective) {
        this.elective = elective;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<ClassRoom> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(List<ClassRoom> classRoom) {
        this.classRooms = classRoom;
    }

}
