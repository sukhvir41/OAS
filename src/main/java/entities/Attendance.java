/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * @author sukhvir
 */
@Entity(name = "Attendance")
@Table(name = "attendance")
public class Attendance implements Serializable {

    @EmbeddedId
    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NonNull
    private Id id;

    @Column(name = "marked_by_teacher", nullable = false)
    @Getter
    @Setter
    private boolean markedByTeacher = false;

    @Column(name = "attended", nullable = false)
    @Getter
    @Setter
    private boolean attended;

    @Column(name = "leave", nullable = false)
    @Getter
    @Setter
    private boolean leave = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("lectureId")
    @JoinColumn(name = "lecture_fid", foreignKey = @ForeignKey(name = "lecture_foreign_key"), nullable = false)
    @NonNull
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("studentId")
    @JoinColumn(name = "student_fid", foreignKey = @ForeignKey(name = "student_foreign_key"), nullable = false)
    @NonNull
    private Student student;


    public Attendance() {
    }

    public Attendance(Lecture theLecture, Student theStudent, boolean attended) {
        this.lecture = theLecture;
        this.student = theStudent;
        this.attended = attended;
        this.id = new Id(lecture.getId(), student.getId());
    }

    public Attendance(
            @NonNull Lecture lecture,
            @NonNull Student student,
            boolean attended,
            boolean markedByTeacher,
            boolean leave) {
        this.markedByTeacher = markedByTeacher;
        this.attended = attended;
        this.leave = leave;
        this.lecture = lecture;
        this.student = student;
        this.id = new Id(lecture.getId(), student.getId());
    }

    protected void setLecture(Lecture theLecture) {
        this.id.setLectureId(theLecture.getId());
    }

    protected void setStudent(Student theStudent) {
        this.id.setStudentId(theStudent.getId());
    }

    public Lecture getLecture() {
        return this.lecture;
    }

    public Student getStudent() {
        return this.student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return lecture.equals(that.lecture) &&
                student.equals(that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lecture, student);
    }

    @Embeddable
    public static class Id implements Serializable {


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

        private Id() {
        }

        public Id(String lectureId, UUID studentId) {
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
            Id that = (Id) o;
            return lectureId.equals(that.lectureId) &&
                    studentId.equals(that.studentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(lectureId, studentId);
        }
    }

}
