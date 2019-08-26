/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.Subject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sukhvir
 * <p>
 * used to show the pie chat attendance at the student attendance pages
 */
public class Holder {

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Subject subject;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int present;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int absent;

}
