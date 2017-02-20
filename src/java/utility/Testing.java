/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.AdminType;
import entities.Attendance;
import entities.ClassRoom;
import entities.Login;
import entities.Student;
import entities.UserType;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sukhvir
 */
public class Testing {

    public static void main(String[] args) throws Exception {
        Login l = new Login("sdfgsdf","asdfas", UserType.Admin, 0,"sdfsdf");
        l.setAdminType(AdminType.Sub);
                

    }

}
