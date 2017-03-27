/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "attendance")
public class Attendance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    @Getter @Setter
    private long id;

    @Column(name = "marked_ny_teacher")
    @Getter @Setter
    private boolean markedByTeacher = false;

    @Column(name = "attended")
    @Getter @Setter
    private boolean attended;

    @Column(name = "leave")
    @Getter @Setter
    private boolean leave = false;

    @ManyToOne
    @JoinColumn(name = "lecture_fid")
    @Getter @Setter
    private Lecture lecture;

    @ManyToOne
    @JoinColumn(name = "student_fid")
    @Getter @Setter
    private Student student;

    public Attendance() {
    }

    /**
     * this sets the attendance to true
     */
    public Attendance(Lecture lecture, Student student) {
        addLecture(lecture);
        addStudent(student);
        attended = true;
    }

    public Attendance(boolean attended, Lecture lecture, Student student) {
        this.attended = attended;
        addLecture(lecture);
        addStudent(student);
    }

    public Attendance(boolean attended, Lecture lecture, Student student, boolean markedByTeacher) {
        this.attended = attended;
        addLecture(lecture);
        addStudent(student);
        this.markedByTeacher = markedByTeacher;
    }

    /**
     * this methods adds the student to attendance and vice versa
     */
    public final void addStudent(Student student) {
        student.addAttendance(this);
    }

    /**
     * this methods adds attendance to lecture and vice versa
     */
    public final void addLecture(Lecture lecture) {
        lecture.addAttendance(this);
    }
}
