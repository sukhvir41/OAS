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

@Entity(name = "Teacher")
@Table(name = "teacher",
        indexes = {
                @Index(name = "teacher_name_index", columnList = "first_name,last_name")
        }
)
public class Teacher implements Serializable {

    @Id
    @Getter
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "id", nullable = false, foreignKey = @ForeignKey(name = "user_teacher_foreign_key"))
    @Getter
    private User user;

    @Column(name = "first_name", nullable = false, length = 40)
    @Getter
    @Setter
    private String fName;

    @Column(name = "last_name", nullable = false, length = 40)
    @Getter
    @Setter
    private String lName;

    @OneToMany(mappedBy = "hod", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<Department> hodOf = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "class_teacher_class_room_link",
            joinColumns = {@JoinColumn(name = "class_teacher_fid", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "class_teacher_class_room_link_class_teacher_foreign_key"))},
            inverseJoinColumns = {@JoinColumn(name = "class_room_fid", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "class_teacher_class_room_link_class_room_foreign_key"))},
            foreignKey = @ForeignKey(name = "class_teacher_class_room_link_class_teacher_foreign_key"),
            inverseForeignKey = @ForeignKey(name = "class_teacher_class_room_link_class_room_foreign_key"),
            indexes = {
                    @Index(name = "class_teacher_class_room_link_class_teacher_index", columnList = "class_teacher_fid"),
                    @Index(name = "class_teacher_class_room_link_class_room_index", columnList = "class_room_fid")
            }
    )
    @Getter
    @Setter
    private ClassRoom classRoom; // class teacher classroom

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Teaching> teaches = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<TeacherDepartmentLink> departments = new HashSet<>();

    public Teacher() {
    }

    public Teacher(String fName, String lName, String username, String password, String email, long number) {
        this.user = User.createdBlockedUser(username, password, email, number, UserType.Teacher);
        this.fName = fName;
        this.lName = lName;
    }

    public boolean isHod() {
        return !hodOf.isEmpty();
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
        this.departments.add(new TeacherDepartmentLink(this, department));
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
