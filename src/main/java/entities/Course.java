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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.graph.RootGraph;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author sukhvir
 */
@Entity(name = "Course")
@Table(name = "course")
public class Course implements Serializable {

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
    @JoinColumn(name = "department_fid", foreignKey = @ForeignKey(name = "department_foreign_key"))
    @Getter
    @Setter
    private Department department;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @OrderBy("name")
    @Getter
    @Setter
    private List<ClassRoom> classRooms = new ArrayList<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @OrderBy("name")
    @Getter
    @Setter
    private List<Subject> subjects = new ArrayList<>();

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
