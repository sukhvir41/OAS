/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.Gson;

/**
 *
 * @author sukhvir
 */
public class Testing {
    String name;
    boolean mark;
    public static void main(String[] args) throws Exception {
        Gson g = new Gson();
        String s = "{name:sukhvir}";
        Testing test = g.fromJson(s, Testing.class);
        System.out.println(test.getName());
        System.out.println(test.isMark());
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }
    
    
    
    

    
}
