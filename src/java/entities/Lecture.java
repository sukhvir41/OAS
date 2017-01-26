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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Kalpesh
 */
@Entity
@Table(name = "lecture")
public class Lecture implements Serializable {

    @Id
    @Column(name = "lec_id")
    private String id;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "tcs_fid")
    Teaching teaching;

    @Column(name = "count")
    private int count = 1;

    @OneToMany(mappedBy = "lecture")
    private List<Attendance> attendance = new ArrayList<Attendance>();

    public Lecture() {
    }

    public Lecture(int count, Teaching teaching) {
        this.count = count;
        setTeaching(teaching);
        this.date = new Date();
    }

    public void adddAttendance(Attendance attendance) {
        attendance.setLecture(this);
        this.attendance.add(attendance);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Teaching getTeaching() {
        return teaching;
    }

    public void setTeaching(Teaching teaching) {
        this.teaching = teaching;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
