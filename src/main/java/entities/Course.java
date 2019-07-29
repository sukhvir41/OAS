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
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sukhvir
 */
@Entity(name = "Course")
@Table(name = "course",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_course_name", columnNames = "name")
        },
        indexes = {
                @Index(name = "course_name_index", columnList = "name")
        }
)
public class Course implements Serializable {

    @Id
    @GeneratedValue(generator = "course_sequence")
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "name", length = 40, nullable = false)
    @Getter
    @Setter
    @NonNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_fid", nullable = false, foreignKey = @ForeignKey(name = "department_foreign_key"))
    @Getter
    @Setter
    @NotNull
    private Department department;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<ClassRoom> classRooms = new HashSet<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<Subject> subjects = new HashSet<>();

    public Course() {
    }

    public Course(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    /**
     * this method adds the course to the department
     * OWNER of the relationship
     *
     * @param department department to be added
     */
    public void addDepartment(Department department) {
        this.department = department;
    }

    public void removeDepartment() {
        this.department = null;
    }

    /**
     * this method adds the classroom to the course
     * NOT the owner of the relationship
     *
     * @param classRoom classroom to be added
     */
    public void addClassRoom(ClassRoom classRoom) {
        this.classRooms.remove(classRoom);
        this.classRooms.add(classRoom);
    }

    /**
     * this method adds the subject to the course.
     * NOT the owner of the relationship
     *
     * @param subject subject to be added
     */
    public void addSubject(Subject subject) {
        this.subjects.remove(subject);
        this.subjects.add(subject);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id != null && id.equals(course.id);
    }


    @Override
    public int hashCode() {
        return 31;
    }
}
