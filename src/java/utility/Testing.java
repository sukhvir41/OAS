/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.Lecture;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sukhvir
 */
public class Testing {

    public static void main(String[] args) throws Exception {
        NewMacaddress.setAddresses("08:00:27:5F:F2:E4", "192.168.1.9");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.1") + " 192.168.1.1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.2") + " 192.168.1.2 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.3") + "192.168.1.3 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.1") + " 192.168.1.1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.2") + " 192.168.1.2 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.3") + "192.168.1.3 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.1") + " 192.168.1.1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.2") + " 192.168.1.2 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.3") + "192.168.1.3 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.1") + " 192.168.1.1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.2") + " 192.168.1.2 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.3") + "192.168.1.3 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.1") + " 192.168.1.1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.2") + " 192.168.1.2 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.3") + "192.168.1.3 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.1") + " 192.168.1.1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.2") + " 192.168.1.2 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.3") + "192.168.1.3 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.1") + " 192.168.1.1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.2") + " 192.168.1.2 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(NewMacaddress.getMacAddress("192.168.1.3") + "192.168.1.3 !!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        NewMacaddress.stop();
    }

}
