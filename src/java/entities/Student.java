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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_id")
    private int id;

    @Column(name = "s_rollnumber")
    private int rollNumber;

    @Column(name = "s_fname")
    private String fName;

    @Column(name = "s_lname")
    private String lName;

    @Column(name = "s_number")
    private long number;

    @Column(name = "s_email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "class_fid")
    private ClassRoom classRoom;

    @Column(name = "verified")
    private boolean verified = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_subject_link", joinColumns = @JoinColumn(name = "student_fid"), inverseJoinColumns = @JoinColumn(name = "sub_fid"))
    private List<Subject> subjects = new ArrayList();

    @ManyToMany(mappedBy = "students")
    List<Lecture> lecture = new ArrayList<>();

    public Student() {
    }

    public Student(int rollNumber, String fName, String lName, long number, String email) {
        this.rollNumber = rollNumber;
        this.fName = fName;
        this.lName = lName;
        this.number = number;
        this.email = email;
    }

    public Student(int rollNumber, String fName, String lName, long number, String email, ClassRoom classRoom, Boolean verified) {
        this.rollNumber = rollNumber;
        this.fName = fName;
        this.lName = lName;
        this.number = number;
        this.email = email;
        this.verified = verified;
        setClassRoom(classRoom);
    }

    public void addSubject(Subject subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
            subject.getStudents().add(this);
        }
    }

    public void addClassRoom(ClassRoom classRoom) {
        classRoom.addStudent(this);
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
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

    public void setfName(String fName) {
        this.fName = fName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

}
