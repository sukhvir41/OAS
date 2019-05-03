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
import org.hibernate.graph.RootGraph;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private long id;

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
    @Getter
    @Setter
    private List<ClassRoom> classRooms = new ArrayList<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
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


    public static Course getInstance(long id, Session session, String... fieldNames) {
        RootGraph<Course> graph = session.createEntityGraph(Course.class);
        graph.addAttributeNodes(fieldNames);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<Course> departmentRoot = query.from(Course.class);
        query.where(
                builder.equal(departmentRoot.get(Course_.id), id)
        );

        return session.createQuery(query)
                .applyLoadGraph(graph)
                .getSingleResult();

    }

    public static Course getFullInstance(long id, Session session) {
        return getInstance(id, session, "department", "classRooms", "subjects");
    }




}
