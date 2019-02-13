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
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

/**
 * @author sukhvir
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

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
    @JoinTable(name = "departmentCourseLink",
            joinColumns = @JoinColumn(name = "courseFid"),
            inverseJoinColumns = @JoinColumn(name = "departmentFid"),
            foreignKey = @ForeignKey(name = "departmentCourseLinkCourseForeignKey"),
            inverseForeignKey = @ForeignKey(name = "departmentCourseLinkDepartmentForeignKey"))
    @Getter
    @Setter
    private Department department;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<ClassRoom> classRooms = new ArrayList();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @Getter
    @Setter
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

    @Override
    public String toString() {
        return name;
    }

}
