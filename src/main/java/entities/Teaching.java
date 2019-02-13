/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Getter
    @Setter
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherFid", foreignKey = @ForeignKey(name = "tcsTeacherForeignKey"))
    @Getter
    @Setter
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classFid", foreignKey = @ForeignKey(name = "tcsClassForeignKey"))
    @Getter
    @Setter
    private ClassRoom classRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectFid", foreignKey = @ForeignKey(name = "tcsSubjectForeignKey"))
    @Getter
    @Setter
    private Subject subject;

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
