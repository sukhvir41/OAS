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
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

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
    @Getter @Setter
    private int id;

    @Column(name = "co_name")
    @Getter @Setter
    private String name;

    //remove
    @Column(name = "start_date")
    @Getter @Setter
    private Date start;
    //remove
    @Column(name = "end_date")
    @Getter @Setter
    private Date end;
    //remove
    @Column(name = "started")
    @Getter @Setter
    private boolean started = false;

    @ManyToOne
    @JoinTable(name = "department_course_link", joinColumns = @JoinColumn(name = "course_fid"), inverseJoinColumns = @JoinColumn(name = "department_fid"))
    @Getter @Setter
    private Department department;

    @OneToMany(mappedBy = "course")
    @BatchSize(size = 20)
    @Getter @Setter
    private List<ClassRoom> classRooms = new ArrayList();

    @OneToMany(mappedBy = "course")
    @BatchSize(size = 20)
    @Getter @Setter
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

    @Override
    public String toString() {
        return name;
    }
    
}
