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
public class DefaultVisitor implements Visitor {

    private static DefaultVisitor instance;

    static {
        instance = new DefaultVisitor();
    }

    public static DefaultVisitor getInstance() {
        return instance;
    }

    private DefaultVisitor() {
    }

    @Override
    public <T> T visit(T t) {
        return t;
    }

}
