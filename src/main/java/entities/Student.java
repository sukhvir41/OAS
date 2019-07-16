/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author sukhvir
 */

@Entity(name = "Student")
@Table(name = "student",
        indexes = {
                @Index(name = "student_name_index", columnList = "first_name,last_name")
        }
)
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

    @Column(name = "mac_id", length = 17) // or should it be 18
    @Getter
    @Setter
    private String macId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_fid", foreignKey = @ForeignKey(name = "class_foreign_key"))
    @Getter
    @Setter
    private ClassRoom classRoom;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @Getter
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    @OrderBy("subject.name")
    @Getter
    private Set<StudentSubjectLink> subjects = new HashSet<>();

    private Student() {
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
        this.user = User.createdBlockedUser(username, password, email, number, UserType.Student);
        this.rollNumber = rollNumber;
        this.fName = fName;
        this.lName = lName;
        this.classRoom = classRoom;

    }


    /**
     * adds the attendance to the student
     * NOT the owner of the relationship
     *
     * @param attendance
     */
    public void addAttendance(Attendance attendance) {
        this.attendances.remove(attendance);
        this.attendances.add(attendance);
    }


    /**
     * this method adds the subject to the student
     * OWNER of the relationship
     *
     * @param subject subject to be added
     */
    public void addSubject(Subject subject) {
        this.subjects.add(new StudentSubjectLink(this, subject));
    }

    /**
     * this method removes the subject to the student
     * OWNER of the relationship
     *
     * @param subject subject to be added
     */
    public void removeSubject(Subject subject) {
        this.subjects.remove(subject);
    }

    /**
     * this methods adds the student to the classroom
     * OWNER of the relationship
     *
     * @param classRoom classroom to be added
     */
    public void addClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    /**
     * this methods removes the student from the classroom
     * OWNER of the relationship
     */
    public void removesClassRoom() {
        this.classRoom = null;
    }

    @Override
    public int compareTo(Student student) {
        return Integer.compare(this.getRollNumber(), student.getRollNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id != null && id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
