/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;

/**
 *
 * @author sukhvir
 */
public enum UserType {

    Admin("admin", "admin"),
    Student("student", "student"),
    Teacher("teacher", "teacher");

    final private String type;

    private final AtomicInteger count;

    @Getter
    final private String homeLink;

    private UserType(String type, String theLink) {
        this.type = type;
        this.homeLink = theLink;
        this.count = new AtomicInteger(0);
    }

    @Override
    public String toString() {
        return type;
    }

    public void incrementCount() {
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }

    public void decrementCount() {
        if (count.get() != 0) {
            count.decrementAndGet();
        }
    }

}
