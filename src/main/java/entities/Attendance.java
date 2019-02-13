/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "attendance")
public class Attendance implements Serializable {

    @EmbeddedId
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private AttendanceId id;

    @Column(name = "markedByTeacher")
    @Getter
    @Setter
    private boolean markedByTeacher = false;

    @Column(name = "attended")
    @Getter
    @Setter
    private boolean attended;

    @Column(name = "leave")
    @Getter
    @Setter
    private boolean leave = false;

    public Attendance() {
    }

    public Attendance(AttendanceId id, boolean markedByTeacher, boolean attended, boolean leave) {
        this.id = id;
        this.markedByTeacher = markedByTeacher;
        this.attended = attended;
        this.leave = leave;
    }

    public Attendance(AttendanceId id, boolean attended, boolean leave) {
        this.id = id;
        this.attended = attended;
        this.leave = leave;
    }

    protected void setLecture(Lecture theLecture) {
        this.id.setLecture(theLecture);
    }

    protected void setStudent(Student theStudent) {
        this.id.setStudent(theStudent);
    }

    public Lecture getLecture() {
        return this.id.getLecture();
    }

    public Student getStudent() {
        return this.id.getStudent();
    }
}
