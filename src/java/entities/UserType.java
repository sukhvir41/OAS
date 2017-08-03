/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.AccessLevel;
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

    @Getter(AccessLevel.PUBLIC)
    final private String homeLink;

    private UserType(String type, String theLink) {
        this.type = type;
        this.homeLink = theLink;
    }

    @Override
    public String toString() {
        return type;
    }

}
