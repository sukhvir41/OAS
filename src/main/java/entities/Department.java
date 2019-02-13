/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Getter
    @Setter
    private long id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hodTeacherFid", foreignKey = @ForeignKey(name = "departmentHodTeacherForeignKey"))
    @Getter
    @Setter
    private Teacher hod;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Course> courses = new ArrayList();

    @ManyToMany(mappedBy = "department")
    @Getter
    @Setter
    private Set<Teacher> teachers = new HashSet();

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
