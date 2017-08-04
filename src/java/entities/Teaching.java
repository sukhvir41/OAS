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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "tcs")
public class Teaching implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tcs_id")
    @Getter
    @Setter
    private int id;

    @ManyToOne
    @JoinColumn(name = "teacher_fid")
    @Getter
    @Setter
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "class_fid")
    @Getter
    @Setter
    private ClassRoom classRoom;

    @ManyToOne
    @JoinColumn(name = "subject_fid")
    @Getter
    @Setter
    private Subject subject;

    @OneToMany(mappedBy = "teaching")
    @BatchSize(size = 40)
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
        addTeacher(teacher);
        addClassRoom(classRoom);
        addSubject(subject);
    }

    /**
     * this method adds lecture to teachings and vice versa
     */
    public final void addLecture(Lecture lecture) {
        if (!lectures.contains(lecture)) {
            lectures.add(lecture);
            lecture.setTeaching(this);
        }
    }

    /**
     * this method adds class room to this teaching
     */
    public final void addClassRoom(ClassRoom classRoom) {
        setClassRoom(classRoom);
    }

    /**
     * this method adds subject to teaching
     */
    public final void addSubject(Subject subject) {
        setSubject(subject);
    }

    /**
     * this method adds teaching to teacher and vice versa
     */
    public final void addTeacher(Teacher teacher) {
        teacher.addTeaching(this);
    }

    @Override
    public String toString() {
        return subject + " - " + classRoom;
    }

}
