/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceServices;

import AttendanceWebScokets.StudentWsSession;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.ArpOperation;

/**
 *
 * @author development
 */
public class MacAddressReceiver implements PacketListener {

    @Override
    public void gotPacket(Packet packet) {
        if (packet.contains(ArpPacket.class)) {
            ArpPacket arp = packet.get(ArpPacket.class);
            if (arp.getHeader().getOperation().equals(ArpOperation.REPLY)) {
                String destinationMacAddress = arp.getHeader().getSrcHardwareAddr().toString();
                String destinationIpAddress = arp.getHeader().getSrcProtocolAddr().toString();
                
                StudentWsSession studentSession = pendingSession.get(destinationIpAddress);
               
                        
                
                
            }
        }
    }

    private static Map<InetAddress, StudentWsSession> pendingSession = new ConcurrentHashMap<>();

    public static void addToQueue(InetAddress ipAddress, StudentWsSession session) {
        pendingSession.put(ipAddress, session);
    }

}
