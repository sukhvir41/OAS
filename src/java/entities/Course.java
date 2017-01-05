/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_id")
    private int id;

    @Column(name = "co_name")
    private String name;

    @Column(name = "start_date")
    private Date start;

    @Column(name = "end_date")
    private Date end;

    @Column(name = "started")
    private boolean started = false;

    @ManyToOne
    @JoinColumn(name = "department_fid")
    private Department department;

    @OneToMany(mappedBy = "course")
    private List<ClassRoom> classRooms = new ArrayList();

    @OneToMany
    @JoinTable(name = "course_subject_link", joinColumns = @JoinColumn(name = "course_fid"), inverseJoinColumns = @JoinColumn(name = "subkect_fid"))
    private List<Subject> subjects = new ArrayList();

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public Course(String name, Department department) {
        this.name = name;
        addDepartment(department);
    }

    /**
     * this method adds the course to the department and the department to the
     * course
     *
     * @param department department to be added
     */
    public void addDepartment(Department department) {
        department.addCourse(this);
    }

    /**
     * this method adds the classroom to the course and the course to the
     * classroom
     *
     * @param classRoom classroom to be added
     */
    public void addClassRoom(ClassRoom classRoom) {
        if (!classRooms.contains(classRoom)) {
            this.classRooms.add(classRoom);
            classRoom.setCourse(this);
        }
    }

    /**
     * this method adds the subject to the course and the course to the subject
     *
     * @param subject subject to be added
     */
    public void addSubject(Subject subject) {
        if (!subjects.contains(subject)) {
            this.subjects.add(subject);
            subject.setCourse(this);
        }
    }

    /**
     * this methods starts the course if not started
     */
    public void startCourse() {
        if (!this.started) {
            this.start = new Date();
            this.end = null;
            this.started = true;
        }
    }

    /**
     * this method stops the course if not stopped
     */
    public void stopCourse() {
        if (started) {
            this.end = new Date();
            this.started = false;
        }
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<ClassRoom> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(List<ClassRoom> classRooms) {
        this.classRooms = classRooms;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

}
