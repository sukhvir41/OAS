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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "id")
public class Student extends User implements Comparable<Student> {

    @Column(name = "s_rollnumber")
    @Getter
    @Setter
    private int rollNumber;

    @Column(name = "s_fname")
    @Getter
    @Setter
    private String fName;

    @Column(name = "s_lname")
    @Getter
    @Setter
    private String lName;

    @Column(name = "mac_id")
    @Getter
    @Setter
    private String macId;

    @Column(name = "unaccounted")
    @Getter
    @Setter
    private boolean unaccounted;

    @ManyToOne
    @JoinTable(name = "class_student_link", joinColumns = @JoinColumn(name = "student_fid"), inverseJoinColumns = @JoinColumn(name = "class_fid"))
    @Getter
    @Setter
    private ClassRoom classRoom;

    @Column(name = "verified")
    @Getter
    @Setter
    private boolean verified;

    @ManyToMany
    @JoinTable(name = "student_subject_link", joinColumns = @JoinColumn(name = "student_fid"), inverseJoinColumns = @JoinColumn(name = "sub_fid"))
    @Getter
    @Setter
    private List<Subject> subjects = new ArrayList();

    @OneToMany(mappedBy = "student")
    @Getter
    @Setter
    private List<Attendance> attendance = new ArrayList<>();

    public Student() {
    }

    public Student(int rollNumber, String fName, String lName, ClassRoom classRoom, String username, String password, String email, long number) {
        super(username, password, email, number);
        this.rollNumber = rollNumber;
        this.fName = fName;
        this.lName = lName;
        addClassRoom(classRoom);
        setVerified(false);
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

    @Override
    public User accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public final UserType getUserType() {
        return UserType.Student;
    }

}
