/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;

/**
 *
 * @author sukhvir
 */
public class ApplicationStats {

    @Getter
    private AtomicInteger students; // total number of students
    @Getter
    private AtomicInteger admins; // total number of admins
    @Getter
    private AtomicInteger teachers;//total number of teacher

    public ApplicationStats() {
        this.students = new AtomicInteger(0);
        this.admins = new AtomicInteger(0);
        this.teachers = new AtomicInteger(0);
    }

    public void incrementStudent() {
        students.addAndGet(1);
    }

    public void incrementTeacher() {
        teachers.addAndGet(1);
    }

    public void incrementAdmin() {
        admins.addAndGet(1);
    }

    public void decrementAdmin() {
        admins.decrementAndGet();
    }

    public void decrementStudent() {
        students.decrementAndGet();
    }

    public void decrementTeacher() {
        teachers.decrementAndGet();
    }

}
