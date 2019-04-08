/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servletListners;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import AttendanceServices.MacHandlers;
import admin.wsServices.SystemInfoService;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
public class ContextListner implements ServletContextListener {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("ready", false);
        executorService.scheduleWithFixedDelay(new SystemInfoService(), 10, 15, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Utils.closeSessionFactory();
        MacHandlers.closeHandles();
        executorService.shutdown();
    }

}
