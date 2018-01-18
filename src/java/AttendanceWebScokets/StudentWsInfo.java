/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceWebScokets;

import entities.Lecture;
import entities.Student;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author development
 */
public class StudentWsInfo {

    @Getter
    @Setter
    private Student student;

    @Getter
    @Setter
    private Lecture Lecutre;

    @Getter
    @Setter
    private LocalDateTime time;

    public StudentWsInfo(Student student, Lecture Lecutre, LocalDateTime theTime) {
        this.student = student;
        this.Lecutre = Lecutre;
        time = theTime;
    }

}
