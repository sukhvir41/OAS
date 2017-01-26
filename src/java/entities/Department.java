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
@Table(name = "department")
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_id")
    private int id;

    @Column(name = "d_name")
    private String name;

    @ManyToOne
    @JoinTable(name = "department_hod_link", joinColumns = @JoinColumn(name = "department_fid"), inverseJoinColumns = @JoinColumn(name = "teacher_hod_fid"))
    private Teacher hod;

    @OneToMany(mappedBy = "department")
    private List<Course> courses = new ArrayList();

    @ManyToMany(mappedBy = "department")
    private List<Teacher> teachers = new ArrayList();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    /**
     * this methods adds the teacher to the department
     *
     * @param teacher teacher to be added to the department
     */
    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            this.teachers.add(teacher);
            teacher.getDepartment().add(this);
        }
    }

    /**
     * this method adds the course to the department and the department to the
     * course
     *
     * @param course course to be added
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            this.courses.add(course);
            course.setDepartment(this);
        }
    }

    public void addHod(Teacher teacher) {
        teacher.addHodOf(this);
    }

    public Teacher getHod() {
        return hod;
    }

    public void setHod(Teacher hod) {
        this.hod = hod;
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

}
