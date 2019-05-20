/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kalpesh
 */

@NamedNativeQueries(
        @NamedNativeQuery(name = "getLectureId", query = "select get_lecture_id()")
)
@Entity(name = "Lecture")
@Table(name = "lecture")
public class Lecture implements Serializable {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "lectureIdGenerator",
            strategy = "entities.LectureIdGenerator")
    @GeneratedValue(generator = "lectureIdGenerator")
    @Getter
    @Setter
    private String id;

    @Column(name = "date", nullable = false)
    @Getter
    @Setter
    @NonNull
    private LocalDateTime date;

    @Column(name = "count", nullable = false)
    @Getter
    @Setter
    private int count = 1;

    @Column(name = "ended", nullable = false)
    @Getter
    @Setter
    private boolean ended = false;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tcs_fid", foreignKey = @ForeignKey(name = "lecture_tcs_foreign_key"))
    @Getter
    @Setter
    Teaching teaching;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    @Getter
    private List<Attendance> attendances = new ArrayList<>();

    public Lecture() {
    }

    public Lecture(int count, Teaching teaching) {
        this.count = count;
        addTeaching(teaching);
        this.date = LocalDateTime.now();
    }

    /**
     * adds teaching to the lecture
     * OWNER of the relationship
     *
     * @param teaching
     */
    public final void addTeaching(Teaching teaching) {
        this.teaching = teaching;
    }

    /**
     * removes teaching from the lecture
     * OWNER of the relationship
     */
    public void removeTeaching() {
        this.teaching = null;
    }

    /**
     * add attendance to the lecture
     * NOT the owner of the relationship
     *
     * @param attendance
     */
    public void addAttendance(Attendance attendance) {
        this.attendances.remove(attendance);
        this.attendances.add(attendance);

    }

    @Override
    public String toString() {
        return getId() + " " + getTeaching();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lecture lecture = (Lecture) o;
        return id != null && id.equals(lecture.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


}
