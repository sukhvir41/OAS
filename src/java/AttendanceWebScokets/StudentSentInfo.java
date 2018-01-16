/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceWebScokets;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author development
 */
public class StudentSentInfo {
    @Getter @Setter
    int studentId;
    
    @Getter @Setter
    int lectureId;

    public StudentSentInfo(int studentId, int lectureId) {
        this.studentId = studentId;
        this.lectureId = lectureId;
    }
    
    
    
}
