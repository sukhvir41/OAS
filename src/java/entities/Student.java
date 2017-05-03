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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "student")
public class Student implements Serializable, Comparable<Student> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_id")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int id;

    @Column(name = "s_rollnumber")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int rollNumber;

    @Column(name = "s_fname")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String fName;

    @Column(name = "s_lname")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String lName;

    @Column(name = "s_number")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private long number;

    @Column(name = "s_email")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String email;

    @Column(name = "mac_id")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String macId;

    @Column(name = "unaccounted")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private boolean unaccounted;

    @ManyToOne
    @JoinTable(name = "class_student_link", joinColumns = @JoinColumn(name = "student_fid"), inverseJoinColumns = @JoinColumn(name = "class_fid"))
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private ClassRoom classRoom;

    @Column(name = "verified")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private boolean verified = false;

    @ManyToMany
    @JoinTable(name = "student_subject_link", joinColumns = @JoinColumn(name = "student_fid"), inverseJoinColumns = @JoinColumn(name = "sub_fid"))
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private List<Subject> subjects = new ArrayList();

    @OneToMany(mappedBy = "student")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private List<Attendance> attendance = new ArrayList<>();

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
        addClassRoom(classRoom);
    }

    public void unaccount() {
        if (!verified) {
            unaccounted = true;
        }
    }

    /**
     * this method adds attendance to the student and vice versa
     */
    public void addAttendance(Attendance attendance) {
        attendance.setStudent(this);
        this.attendance.add(attendance);
    }

    /**
     * this method adds the subject to the student the subject to the student
     *
     * @param subject subject to be added
     */
    public void addSubject(Subject subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
            subject.getStudents().add(this);
        }
    }

    /**
     * this methods adds the student to the classroom the classroom to the
     * student
     *
     * @param classRoom classroom to be added
     */
    final public void addClassRoom(ClassRoom classRoom) {
        classRoom.addStudent(this);
    }

    @Override
    public String toString() {
        return fName + " " + lName;
    }

    @Override
    public int compareTo(Student student) {
        return this.getRollNumber() < student.getRollNumber() ? -1 : (this.getRollNumber() == student.getRollNumber()) ? 0 : 1;
    }

}
