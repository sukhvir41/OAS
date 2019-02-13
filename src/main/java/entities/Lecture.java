/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

/**
 *
 * @author Kalpesh
 */
@Entity
@Table(name = "lecture")
public class Lecture implements Serializable{

    @Id
    @Column(name = "id")
    @Getter
    @Setter
    private String id;

    @Column(name = "date")
    @Getter
    @Setter
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tcsFid", foreignKey = @ForeignKey(name = "lectureTcsForeignKey"))
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

    public Lecture() {
    }

    public Lecture(int count, Teaching teaching) {
        this.count = count;
        addTeaching(teaching);
        this.date = LocalDateTime.now();
    }

    public final void addTeaching(Teaching teaching) {
        teaching.addLecture(this);
    }

    @Override
    public String toString() {
        return getId() + " "+ getTeaching();
    }  
    
}
