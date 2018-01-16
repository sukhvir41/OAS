/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceWebScokets;

import entities.Lecture;
import entities.Student;
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

    public StudentWsInfo(Student student, Lecture Lecutre) {
        this.student = student;
        this.Lecutre = Lecutre;
    }

}
