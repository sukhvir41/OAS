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

    private static final DefaultVisitor INSTANCE;

    static {
        INSTANCE = new DefaultVisitor();
    }

    public static DefaultVisitor getInstance() {
        return INSTANCE;
    }

    private DefaultVisitor() {
    }

    @Override
    public <T> T visit(T t) {
        return t;
    }

}
