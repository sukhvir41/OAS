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
    private long id;

    @Column(name = "marked_ny_teacher")
    private boolean markedByTeacher = false;

    @Column(name = "attended")
    private boolean attended;

    @Column(name = "leave")
    private boolean leave = false;

    @ManyToOne
    @JoinColumn(name = "lecture_fid")
    private Lecture lecture;

    @ManyToOne
    @JoinColumn(name = "student_fid")
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isMarkedByTeacher() {
        return markedByTeacher;
    }

    public void setMarkedByTeacher(boolean markedByTeacher) {
        this.markedByTeacher = markedByTeacher;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isLeave() {
        return leave;
    }

    public void setLeave(boolean leave) {
        this.leave = leave;
    }

}
