/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "teacher")
@PrimaryKeyJoinColumn(name = "id")
public class Teacher extends User {

    @Column(name = "t_fname")
    @Getter
    @Setter
    private String fName;

    @Column(name = "t_lname")
    @Getter
    @Setter
    private String lName;

    @Column(name = "verified")
    @Getter
    @Setter
    private boolean verified;

    @Column(name = "unaccounted")
    @Getter
    @Setter
    private boolean unaccounted;

    @Column(name = "t_hod")
    @Getter
    @Setter
    private boolean hod;

    @OneToMany(mappedBy = "hod", fetch = FetchType.EAGER)
    @Getter
    @Setter
    private List<Department> hodOf = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "class_fid")
    @Getter
    @Setter
    private ClassRoom classRoom;

    @OneToMany(mappedBy = "teacher")
    @BatchSize(size = 20)
    @Getter
    @Setter
    private List<Teaching> teaches = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "teacher_department_link", joinColumns = @JoinColumn(name = "teacher_fid"), inverseJoinColumns = @JoinColumn(name = "department_fid"))
    @BatchSize(size = 2)
    @Getter
    @Setter
    private List<Department> department = new ArrayList<>();

    public Teacher() {
    }

    public Teacher(String fName, String lName, String username, String password, String email, long number) {
        super(username, password, email, number);
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
     * this method adds department hod to teacher and vice versa but does not
     * mark hod to true
     */
    public void addHodOf(Department department) {
        if (!hodOf.contains(department)) {
            hodOf.add(department);
            department.setHod(this);
            hod = true;
        }
    }

    @Override
    public String toString() {
        return fName + " " + lName;
    }

    @Override
    public final UserType getUserType() {
        return UserType.Teacher;
    }

}
