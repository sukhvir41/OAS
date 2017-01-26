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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "class")
public class ClassRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private int id;

    @Column(name = "c_name")
    private String name;

    @Column(name = "c_div")
    private String division;

    @Column(name = "c_sem")
    private int semister;

    @Column(name = "c_minimum_subs")
    private int minimumSubecjts;

    @ManyToOne
    private Course course;
    
    @OneToOne(mappedBy = "classRoom")
    private Teacher classTeacher;

    @OneToMany
    @JoinTable(name = "student_calssroom_link",joinColumns = @JoinColumn(name = "class_fid"),inverseJoinColumns = @JoinColumn(name = "student_fid"))
    private List<Student> students = new ArrayList();

    @ManyToMany
    @JoinTable(name = "subject_class_link", joinColumns = @JoinColumn(name = "class_fid"), inverseJoinColumns = @JoinColumn(name = "subject_fid"))
    private List<Subject> subjects = new ArrayList<>();

    public ClassRoom() {
    }

    public ClassRoom(String name, String division, int semister, int minimumSubecjts) {
        this.name = name;
        this.division = division;
        this.semister = semister;
        this.minimumSubecjts = minimumSubecjts;
    }

    public ClassRoom(String name, String division, int semister, Course course) {
        this.name = name;
        this.division = division;
        this.semister = semister;
        this.course = course;
    }

    public ClassRoom(String name, String division, int semister, Course course, int minimumSubjects) {
        this.name = name;
        this.division = division;
        this.semister = semister;
        this.minimumSubecjts = minimumSubjects;
        addCourse(course);
    }

    /**
     * this method adds the classroom to the course and the course to the
     * classroom
     */
    public void addCourse(Course course) {
        course.addClassRoom(this);
    }

    /**
     * this method adds the student to the classroom and the class room to the
     * student
     */
    public void addStudent(Student student) {
        if (!students.contains(student)) {
            this.students.add(student);
            student.setClassRoom(this);
        }
    }

    /**
     * this method adds the subject to ten classroom
     *
     * @param subject subject to be added
     */
    public void addSubject(Subject subject) {
        if (!subjects.contains(subject)) {
            this.subjects.add(subject);
        }
    }

    public int getMinimumSubecjts() {
        return minimumSubecjts;
    }

    public void setMinimumSubecjts(int minimumSubecjts) {
        this.minimumSubecjts = minimumSubecjts;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getSemister() {
        return semister;
    }

    public void setSemister(int semister) {
        this.semister = semister;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Teacher getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(Teacher classTeacher) {
        this.classTeacher = classTeacher;
    }
    
}
