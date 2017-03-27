/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "teacher")
public class Teacher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_id")
    @Getter
    @Setter
    private int id;

    @Column(name = "t_fname")
    @Getter
    @Setter
    private String fName;

    @Column(name = "t_lname")
    @Getter
    @Setter
    private String lName;

    @Column(name = "t_number")
    @Getter
    @Setter
    private long number;

    @Column(name = "t_email")
    @Getter
    @Setter
    private String email;

    @Column(name = "verified")
    @Getter
    @Setter
    private boolean verified = false;

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
    @Getter
    @Setter
    private List<Teaching> teaches = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "teacher_department_link", joinColumns = @JoinColumn(name = "teacher_fid"), inverseJoinColumns = @JoinColumn(name = "department_fid"))
    @Getter
    @Setter
    private List<Department> department = new ArrayList<>();

    public Teacher() {
    }

    public Teacher(String fName, String lName, long number, String email, boolean hod) {
        this.fName = fName;
        this.lName = lName;
        this.number = number;
        this.email = email;
        this.hod = hod;
    }

    public Teacher(String fName, String lName, long number, String email, boolean verified, boolean hod, ClassRoom classRoom) {
        this.fName = fName;
        this.lName = lName;
        this.number = number;
        this.email = email;
        this.verified = verified;
        this.hod = hod;
        addClassRoom(classRoom);
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

}
