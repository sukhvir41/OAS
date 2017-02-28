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
    private int id;

    @Column(name = "t_fname")
    private String fName;

    @Column(name = "t_lname")
    private String lName;

    @Column(name = "t_number")
    private long number;

    @Column(name = "t_email")
    private String email;

    @Column(name = "verified")
    private boolean verified = false;

    @Column(name = "t_hod")
    private boolean hod;

    @OneToMany(mappedBy = "hod")
    private List<Department> hodOf = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "class_fid")
    private ClassRoom classRoom;

    @OneToMany(mappedBy = "teacher")
    private List<Teaching> teaches = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "teacher_department_link", joinColumns = @JoinColumn(name = "teacher_fid"), inverseJoinColumns = @JoinColumn(name = "department_fid"))
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
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fNname) {
        this.fName = fNname;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isHod() {
        return hod;
    }

    public void setHod(boolean hod) {
        this.hod = hod;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Teaching> getTeaches() {
        return teaches;
    }

    public void setTeaches(List<Teaching> teaches) {
        this.teaches = teaches;
    }

    public List<Department> getDepartment() {
        return department;
    }

    public void setDepartment(List<Department> department) {
        this.department = department;
    }

    public List<Department> getHodOf() {
        return hodOf;
    }

    public void setHodOf(List<Department> hodOf) {
        this.hodOf = hodOf;
    }

}
