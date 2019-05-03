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

@NamedEntityGraph(name = "userStudent",
        attributeNodes = @NamedAttributeNode("user"))
@Entity(name = "Student")
@Table(name = "student")
public class Student implements Serializable, Comparable<Student> {

    @Id
    @Getter
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "id", nullable = false, foreignKey = @ForeignKey(name = "user_student_foreign_key"))
    @Getter
    private User user;


    @Column(name = "roll_number", nullable = false)
    @Getter
    @Setter
    private int rollNumber;

    @Column(name = "first_name", nullable = false, length = 40)
    @Getter
    @Setter
    private String fName;

    @Column(name = "last_name", nullable = false, length = 40)
    @Getter
    @Setter
    private String lName;

    @Column(name = "mac_id", length = 17) // or should 18 ?
    @Getter
    @Setter
    private String macId;

    @Column(name = "unaccounted")
    @Getter
    @Setter
    private boolean unaccounted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_fid", foreignKey = @ForeignKey(name = "class_foreign_key"))
    @Getter
    @Setter
    private ClassRoom classRoom;

    @Column(name = "verified", nullable = false)
    @Getter
    @Setter
    private boolean verified;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @Getter
    private List<Attendance> attendances = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_subject_link",
            joinColumns = @JoinColumn(name = "student_fid"),
            inverseJoinColumns = @JoinColumn(name = "subject_fid"),
            foreignKey = @ForeignKey(name = "student_subject_link_student_foreign_key"),
            inverseForeignKey = @ForeignKey(name = "student_subject_link_subject_foreign_key"))
    @Getter
    @Setter
    private Set<Subject> subjects = new HashSet<>();

    public Student() {
    }

    public Student(
            int rollNumber,
            String fName,
            String lName,
            ClassRoom classRoom,
            String username,
            String password,
            String email,
            long number) {
        this.user = new User(username, password, email, number, UserType.Student);
        this.rollNumber = rollNumber;
        this.fName = fName;
        this.lName = lName;
        //addClassRoom( classRoom );
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
        return Integer.compare(this.getRollNumber(), student.getRollNumber());
    }

}
