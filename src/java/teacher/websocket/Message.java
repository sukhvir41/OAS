/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher.websocket;

/**
 *
 * @author sukhvir
 */
public class Message {

    private String lectureId;
    private int studentId;
    private boolean mark;
    private boolean markedByTeacher;

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public boolean isMarkedByTeacher() {
        return markedByTeacher;
    }

    public void setMarkedByTeacher(boolean markedByTeacher) {
        this.markedByTeacher = markedByTeacher;
    }

}
