/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servletListners;

import admin.wsServices.SystemInfoService;
import student.attendanceWsService.MacHandlers;
import utility.AddToDatabase;
import utility.Utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author sukhvir
 */
public class ContextListner implements ServletContextListener {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("ready", false);
        // used fro send system info to admin (home page) using web sockets
        addToDb();
        executorService.scheduleWithFixedDelay(new SystemInfoService(), 10, 15, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Utils.closeSessionFactory();
        MacHandlers.closeHandles();
        executorService.shutdown();
    }


    private void addToDb() {

        AddToDatabase.main(null);
    }

}
