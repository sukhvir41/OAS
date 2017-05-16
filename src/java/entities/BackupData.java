/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "Backup_data")
public class BackupData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private long id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "classroom")
    @Getter
    @Setter
    private String classRoom;

    @Column(name = "rollnumber")
    @Getter
    @Setter
    private int rollNumber;

    @Column(name = "data")
    @Getter
    @Setter
    private String data;

    @Column(name = "backup_name")
    @Getter
    @Setter
    private String backupName;// this will be in json format

    @Column(name = "date")
    @Convert(converter = LocalDateTimeConverter.class)
    @Getter
    @Setter
    private LocalDateTime date;

    public BackupData(String name, String classRoom, int rollNumber, String data, String backupName, LocalDateTime date) {
        this.name = name;
        this.classRoom = classRoom;
        this.rollNumber = rollNumber;
        this.data = data;
        this.backupName = backupName;
        this.date = date;
    }

}
