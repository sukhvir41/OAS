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
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

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
    @Getter
    @Setter
    private int id;

    @Column(name = "d_name")
    @Getter
    @Setter
    private String name;

    @ManyToOne
    @JoinTable(name = "department_hod_link", joinColumns = @JoinColumn(name = "department_fid"), inverseJoinColumns = @JoinColumn(name = "teacher_hod_fid"))
    @Getter
    @Setter
    private Teacher hod;

    @OneToMany(mappedBy = "department")
    @BatchSize(size = 20)
    @Getter
    @Setter
    private List<Course> courses = new ArrayList();

    @ManyToMany(mappedBy = "department")
    @BatchSize(size = 20)
    @Getter
    @Setter
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

    /**
     * this method adds the teacher as hod of department and vice versa
     */
    public void addHod(Teacher teacher) {
        teacher.addHodOf(this);
    }

    @Override
    public String toString() {
        return name;
    }

}
