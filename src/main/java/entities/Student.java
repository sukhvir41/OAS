/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.FetchProfile;
import org.junit.FixMethodOrder;

/**
 * @author sukhvir
 */
@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "id")
public class Student extends User implements Comparable<Student> {

    @Column(name = "rollnumber")
    @Getter
    @Setter
    private int rollNumber;

    @Column(name = "fName")
    @Getter
    @Setter
    private String fName;

    @Column(name = "lName")
    @Getter
    @Setter
    private String lName;

    @Column(name = "macId")
    @Getter
    @Setter
    private String macId;

    @Column(name = "unaccounted")
    @Getter
    @Setter
    private boolean unaccounted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classFid", foreignKey = @ForeignKey(name = "studentForeignKey"))
    @Getter
    @Setter
    private ClassRoom classRoom;

    @Column(name = "verified")
    @Getter
    @Setter
    private boolean verified;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "studentSubjectLink",
            joinColumns = @JoinColumn(name = "studentFid"),
            inverseJoinColumns = @JoinColumn(name = "subjectFid"),
            foreignKey = @ForeignKey(name = "studentSubjectLinkStudentForeignKey"),
            inverseForeignKey = @ForeignKey(name = "studentSubjectLinkSubjectForeignKey"))
    @Getter
    @Setter
    private Set<Subject> subjects = new HashSet<>();

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
    public final UserType getUserType() {
        return UserType.Student;
    }

}
