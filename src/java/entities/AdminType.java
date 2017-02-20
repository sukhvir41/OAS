/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author sukhvir
 */
public enum AdminType {
    Main("main"),
    Sub("sub");

    final private String type;

    private AdminType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

}
