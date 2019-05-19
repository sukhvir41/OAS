/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import lombok.Getter;
import org.hibernate.Session;
import oshi.SystemInfo;
import student.attendanceWsService.MacHandlers;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/network-settings")
public class NetworkSettings extends Controller {


    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        SystemInfo info = new SystemInfo();

        var networkInfoList = Stream.of(info.getHardware().getNetworkIFs())
                .map(networkIF -> new Networks(networkIF.getDisplayName(), Arrays.toString(networkIF.getIPv4addr()), networkIF.getMacaddr()))
                .collect(Collectors.toList());

        req.setAttribute("macAddress", MacHandlers.getMachineMac());
        req.setAttribute("ipAddress", MacHandlers.getMachineIp());
        req.setAttribute("handlerReady", MacHandlers.isHandlerReady());
        req.setAttribute("networkInfoList", networkInfoList);

        req.getRequestDispatcher("/WEB-INF/admin/network-settings.jsp")
                .include(req, resp);
    }


    public static class Networks {

        @Getter
        private String displayName;

        @Getter
        private String ipAddresses;

        @Getter
        private String macAddress;

        public Networks(String displayName, String ipAddresses, String macAddress) {
            this.displayName = displayName;
            this.ipAddresses = ipAddresses;
            this.macAddress = macAddress;
        }
    }
}
