/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author development
 */
@Embeddable
public class AttendanceId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lectureFid", foreignKey = @ForeignKey(name = "attendanceLectureForeignKey"))
    @Getter
    @Setter
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentFid", foreignKey = @ForeignKey(name = "attendanceStudentForeignKey"))
    @Getter
    @Setter
    private Student student;

    public AttendanceId(Lecture lecture, Student student) {
        this.lecture = lecture;
        this.student = student;
    }
}
