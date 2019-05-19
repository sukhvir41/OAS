/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.Session;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.*;

/**
 * @author sukhvir
 */

@Entity(name = "Department")
@Table(name = "department")
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "name", length = 40, nullable = false)
    @Getter
    @Setter
    @NonNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hod_teacher_fid", foreignKey = @ForeignKey(name = "department_hod_teacher_foreign_key"))
    @Getter
    @Setter
    private Teacher hod;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @OrderBy("name")
    @Getter
    @Setter
    private List<Course> courses = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("fName ASC, lName ASC")
    @JoinTable(name = "teacher_department_link",
            joinColumns = @JoinColumn(name = "teacher_fid"),
            inverseJoinColumns = @JoinColumn(name = "department_fid"),
            foreignKey = @ForeignKey(name = "teacher_department_link_teacher_foreign_key"),
            inverseForeignKey = @ForeignKey(name = "teacher_department_link_department_foreign_key"))
    @Getter
    @Setter
    private Set<Teacher> teachers = new HashSet<>();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    /**
     * this methods adds the teacher to the department
     * OWNER of the relationship
     *
     * @param teacher teacher to be added to the department
     */
    public void addTeacher(Teacher teacher) {
        // as teacher is a set it will contain uniques
        this.teachers.add(teacher);
    }

    /**
     * this methods removes the teacher to the department
     * OWNER of the relationship
     *
     * @param teacher teacher to be added to the department
     */
    public void removeTeacher(Teacher teacher) {
        // as teacher is a set it will contain uniques
        this.teachers.remove(teacher);
    }

    /**
     * this method adds the course to the department
     * NOT the owner of the relationship
     *
     * @param course course to be added
     */
    public void addCourse(Course course) {
        this.courses.remove(course);
        this.courses.add(course);
    }

    /**
     * this method adds the teacher as hod of department
     * OWNER of the relationship
     */
    public void addHod(Teacher teacher) {

        this.hod = teacher;
    }

    /**
     * this method removes the Hod from the department.
     * OWNER of the relationship
     */
    public void removeHod() {
        this.hod = null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
