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
import javax.persistence.ManyToMany;
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

    @Column(name = "d_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "department")
    private List<Course> courses = new ArrayList();

    @ManyToMany(mappedBy = "department")
    private List<Teacher> teachers = new ArrayList();

    @OneToOne
    @Column(name = "hod")
    private Teacher hod;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            this.teachers.add(teacher);
            teacher.getDepartment().add(this);
        }
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            this.courses.add(course);
            course.setDepartment(this);
        }
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
