/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author development
 */
@Embeddable
public class AttendanceId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "lecture_fid")
    @Getter
    @Setter
    private Lecture lecture;

    @ManyToOne
    @JoinColumn(name = "student_fid")
    @Getter
    @Setter
    private Student student;

    
    
    
}
