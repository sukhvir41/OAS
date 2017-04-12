/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servletListners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import utility.NewMacaddress;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
public class ContextListner implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Utils.closeSesssioFactory();
        NewMacaddress.stop();
        System.out.println("server closed !!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}
