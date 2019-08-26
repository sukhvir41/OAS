/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author sukhvir
 */
@Entity(name = "Teaching")
@Table(name = "tcs",
        indexes = {
                @Index(name = "tcs_teacher_index", columnList = "teacher_fid"),
                @Index(name = "tcs_class_room_index", columnList = "class_room_fid"),
                @Index(name = "tcs_subject_index", columnList = "subject_fid")
        }
)
public class Teaching implements Serializable {


    @EmbeddedId
    @Getter
    private Id id = new Id();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_room_fid", foreignKey = @ForeignKey(name = "tcs_class_room_foreign_key"), nullable = false)
    @MapsId("classRoomId")
    @Getter
    @Setter
    @NonNull
    private ClassRoom classRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_fid", foreignKey = @ForeignKey(name = "tcs_subject_foreign_key"), nullable = false)
    @MapsId("subjectId")
    @Getter
    @Setter
    @NonNull
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_fid", foreignKey = @ForeignKey(name = "tcs_teacher_foreign_key"))
    @Getter
    @Setter
    private Teacher teacher;


    @OneToMany(mappedBy = "teaching", fetch = FetchType.LAZY)
    @Getter
    @Setter
    List<Lecture> lectures = new ArrayList<>();

    public Teaching() {
    }

    public Teaching(ClassRoom classRoom, Subject subject) {
        this.classRoom = classRoom;
        this.subject = subject;
    }

    public Teaching(Teacher teacher, ClassRoom classRoom, Subject subject) {
        this.teacher = teacher;
        this.classRoom = classRoom;
        this.subject = subject;
    }

    /**
     * adds the lecture to teaching
     * NOT the owner of the relationship
     *
     * @param lecture
     */
    public void addLecture(Lecture lecture) {
        this.lectures.remove(lecture);
        this.lectures.add(lecture);
    }


    /**
     * removes the teacher to the teaching
     * OWNER of the relationship
     */

    public void removeTeacher() {
        this.teacher = null;
    }

    /**
     * adds the teacher to the teaching
     * OWNER of the relationship
     *
     * @param teacher
     */
    public void addTeacher(Teacher teacher) {
        this.teacher = teacher;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teaching teaching = (Teaching) o;
        return id != null && id.equals(teaching.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Embeddable
    public static class Id implements Serializable {

        @Column(name = "class_room_fid")
        Long classRoomId;

        @Column(name = "subject_fid")
        Long subjectId;

        protected Id() {
        }

        public Id(Long classRoomId, Long subjectId) {
            this.classRoomId = classRoomId;
            this.subjectId = subjectId;
        }

        public boolean equals(Object o) {
            if (o != null && o instanceof Teaching.Id) {
                Teaching.Id that = (Teaching.Id) o;
                return this.subjectId.equals(that.subjectId)
                        && this.classRoomId.equals(that.classRoomId);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(subjectId, classRoomId);
        }
    }
}
