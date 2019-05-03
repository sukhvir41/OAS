/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.graph.RootGraph;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.*;

/**
 * @author sukhvir
 */

@NamedEntityGraph(name = "userTeacher",
        attributeNodes = @NamedAttributeNode("user"))
@Entity(name = "Teacher")
@Table(name = "teacher")
//@PrimaryKeyJoinColumn(name = "id")
public class Teacher implements Serializable {

    @Id
    @Getter
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "id", nullable = false, foreignKey = @ForeignKey(name = "user_teacher_foreign_key"))
    @Getter
    private User user;

    @Column(name = "f_name", nullable = false, length = 40)
    @Getter
    @Setter
    private String fName;

    @Column(name = "l_name", nullable = false, length = 40)
    @Getter
    @Setter
    private String lName;

    @Column(name = "verified", nullable = false)
    @Getter
    @Setter
    private boolean verified;

    @Column(name = "unaccounted", nullable = false)
    @Getter
    @Setter
    private boolean unaccounted;

    @Column(name = "is_hod", nullable = false)
    @Getter
    @Setter
    private boolean hod = false;

    @OneToMany(mappedBy = "hod", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Department> hodOf = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_fid", foreignKey = @ForeignKey(name = "class_teacher_class_foreign_key"))
    @Getter
    @Setter
    private ClassRoom classRoom; // class teacher classroom

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Teaching> teaches = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "teacher_department_link",
            joinColumns = @JoinColumn(name = "teacher_fid"),
            inverseJoinColumns = @JoinColumn(name = "department_fid"),
            foreignKey = @ForeignKey(name = "teacher_department_link_teacher_foreign_key"),
            inverseForeignKey = @ForeignKey(name = "teacher_department_link_department_foreign_key"))
    @Getter
    @Setter
    private Set<Department> department = new HashSet<>();

    public Teacher() {
    }

    public Teacher(String fName, String lName, String username, String password, String email, long number) {
        //super(username, password, email, number);
        this.user = new User(username, password, email, number, UserType.Teacher);
        this.fName = fName;
        this.lName = lName;
        setVerified(false);

    }

    public void unaccount() {
        if (!verified) {
            unaccounted = true;
        }
    }

    /**
     * this method adds class room to class teacher and vice versa
     */
    public final void addClassRoom(ClassRoom classRoom) {
        classRoom.addClassTeacher(this);
    }

    /**
     * this methods adds the teaching to teacher and vice versa
     */
    public final void addTeaching(Teaching teaching) {
        if (!teaches.contains(teaching)) {
            this.teaches.add(teaching);
            teaching.setTeacher(this);
        }
    }

    /**
     * this methods adds the teacher to the department and the department to the
     * teacher
     *
     * @param department
     */
    public void addDepartment(Department department) {
        department.addTeacher(this);
    }

    /**
     * this method adds department hod to teacher and vice versa.
     */
    public void addHodOf(Department department) {
        department.removeHod();//removing the old hod of the department
        this.getHodOf().remove(department);//removing the department from the current hod of to avoid duplicate entries
        this.getHodOf().add(department);//adding teh department to the hod of
        department.setHod(this);//adding the teacher (HOD) to the department
        setHod(true); //setting the isHod to true
    }

    /**
     * this removes the department from HodOf and sets the department Hod to null.
     */
    public void removeHodOf(Department department) {
        this.hodOf.remove(department);
        department.setHod(null);
        if (getHodOf().isEmpty()) {
            setHod(false);
        }
    }

    @Override
    public String toString() {
        return fName + " " + lName;
    }

    public static Teacher getInstance(UUID id, Session session, String... fieldNames) {
        RootGraph<Teacher> graph = session.createEntityGraph(Teacher.class);
        graph.addAttributeNodes(fieldNames);
        graph.addAttributeNode("user");

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Teacher> criteriaQuery = builder.createQuery(Teacher.class);
        Root<Teacher> teacherRoot = criteriaQuery.from(Teacher.class);
        criteriaQuery.where(
                builder.equal(teacherRoot.get(Teacher_.ID), id)
        );

        return session.createQuery(criteriaQuery)
                .applyLoadGraph(graph)
                .setMaxResults(1)
                .getSingleResult();
    }

    public static Teacher getReadOnlyInstance(UUID id, Session session, String... fieldNames) {
        RootGraph<Teacher> graph = session.createEntityGraph(Teacher.class);

        if (Objects.nonNull(fieldNames) && fieldNames.length > 0) {
            graph.addAttributeNodes(fieldNames);
        }
        graph.addAttributeNode("user");

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Teacher> criteriaQuery = builder.createQuery(Teacher.class);
        Root<Teacher> teacherRoot = criteriaQuery.from(Teacher.class);
        criteriaQuery.where(
                builder.equal(teacherRoot.get(Teacher_.ID), id)
        );

        return session.createQuery(criteriaQuery)
                .applyLoadGraph(graph)
                .setMaxResults(1)
                .setReadOnly(true)
                .getSingleResult();
    }

}
