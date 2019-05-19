/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
    @OrderBy("name")
    @Getter
    @Setter
    private Set<Department> department = new HashSet<>();

    public Teacher() {
    }

    public Teacher(String fName, String lName, String username, String password, String email, long number) {
        this.user = new User(username, password, email, number, UserType.Teacher);
        this.fName = fName;
        this.lName = lName;
        setVerified(false);

    }

    public boolean isHod() {
        return !hodOf.isEmpty();
    }

    public void unaccount() {
        if (!verified) {
            unaccounted = true;
        }
    }

    /**
     * add class room to teacher. by doing so you make the teacher teh class teacher of the class room .
     * (owner of the relationship).
     *
     * @param classRoom
     */
    public final void addClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }


    /**
     * add the teaching to the teacher.
     * (NOT the owner of the relationship)
     *
     * @param teaching
     */
    public final void addTeaching(Teaching teaching) {
        this.teaches.remove(teaching);
        this.teaches.add(teaching);
    }


    /**
     * adds teh teacher to the department
     * (NOT the owner of the relationship)
     *
     * @param department
     */
    public void addDepartment(Department department) {
        //as department is a set it will only get added once
        this.department.add(department);
    }

    /**
     * add hod (teacher ) to the department.
     * (NOT the owner of the relationship)
     *
     * @param department
     */
    public void addHodOf(Department department) {
        this.hodOf.remove(department);
        this.hodOf.add(department);
    }


    /**
     * remove the hod (teacher) from the department
     * (NOT the  owner of the relationship)
     *
     * @param department
     */
    public void removeHodOf(Department department) {
        this.hodOf.remove(department);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id != null && id.equals(teacher.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
