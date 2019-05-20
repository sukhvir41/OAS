/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


/**
 * @author development
 */
@Embeddable
public class AttendanceId implements Serializable {


    @Column(name = "lecture_fid", nullable = false, length = 8) // 8 or 6 ?
    @Getter
    @Setter
    @NonNull
    private String lectureId;

    @Column(name = "student_fid", columnDefinition = "uuid", nullable = false)
    @Getter
    @Setter
    @NonNull
    private UUID studentId;

    private AttendanceId() {
    }

    public AttendanceId(String lectureId, UUID studentId) {
        this.lectureId = lectureId;
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceId that = (AttendanceId) o;
        return lectureId.equals(that.lectureId) &&
                studentId.equals(that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lectureId, studentId);
    }
}
