/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sukhvir
 */

@Entity(name = "Department")
@Table(name = "department",
        uniqueConstraints = {
                @UniqueConstraint(name = "department_unique_name", columnNames = "name")
        },
        indexes = {
                @Index(name = "department_name_index", columnList = "name")
        }
)
public class Department implements Serializable {

    @Id
    @GeneratedValue(generator = "department_sequence")
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "name", length = 40, nullable = false)
    @Getter
    @Setter
    @NonNull
    @Size(min = 2, max = 40)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hod_teacher_fid", foreignKey = @ForeignKey(name = "department_hod_teacher_foreign_key"))
    @Getter
    @Setter
    private Teacher hod;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Course> courses = new ArrayList<>();


    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<TeacherDepartmentLink> teachers = new HashSet<>();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    /**
     * this methods adds the teacher to the department
     * NOT THE OWNER of the relationship
     *
     * @param teacher teacher to be added to the department
     */
    public void addTeacher(Teacher teacher) {
        // as teacher is a set it will contain uniques
        this.teachers.add(new TeacherDepartmentLink(teacher, this));
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
