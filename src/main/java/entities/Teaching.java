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

/**
 * @author sukhvir
 */
@Entity(name = "Teaching")
@Table(name = "tcs", uniqueConstraints = @UniqueConstraint(name = "tcs_unique_constraint", columnNames = {
        "class_fid",
        "subject_fid"
}))
public class Teaching implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_fid", foreignKey = @ForeignKey(name = "tcs_teacher_foreign_key"))
    @Getter
    @Setter
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_fid", foreignKey = @ForeignKey(name = "tcs_class_foreign_key"), nullable = false)
    @Getter
    @Setter
    @NonNull
    private ClassRoom classRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_fid", foreignKey = @ForeignKey(name = "tcs_subject_foreign_key"), nullable = false)
    @Getter
    @Setter
    @NonNull
    private Subject subject;

    @OneToMany(mappedBy = "teaching", fetch = FetchType.LAZY)
    @OrderBy("date")
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
}
