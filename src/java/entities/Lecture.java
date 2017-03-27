/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Kalpesh
 */
@Entity
@Table(name = "lecture")
public class Lecture implements Serializable {

    @Id
    @Column(name = "lec_id")
    @Getter
    @Setter
    private String id;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date date;

    @ManyToOne
    @JoinColumn(name = "tcs_fid")
    @Getter
    @Setter
    Teaching teaching;

    @Column(name = "count")
    @Getter
    @Setter
    private int count = 1;

    @Column(name = "ended")
    @Getter
    @Setter
    private boolean ended = false;

    @OneToMany(mappedBy = "lecture")
    @Getter
    @Setter
    private List<Attendance> attendance = new ArrayList<Attendance>();

    public Lecture() {
    }

    public Lecture(int count, Teaching teaching) {
        this.count = count;
        addTeaching(teaching);
        this.date = new Date();
    }

    public final void addTeaching(Teaching teaching) {
        teaching.addLecture(this);
    }

    /**
     * this method adds attendance to lecture and vice versa
     */
    public void addAttendance(Attendance attendance) {
        attendance.setLecture(this);
        this.attendance.add(attendance);
    }
}
