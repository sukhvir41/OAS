package student.attendanceWsService;

import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.pcap4j.util.MacAddress;

import java.net.InetAddress;

public class MacAddressListner implements PacketListener {

    @Override
    public void gotPacket(Packet packet) {
        if (packet.contains(ArpPacket.class)) {
            ArpPacket arp = packet.get(ArpPacket.class);
            if (arp.getHeader().getOperation().equals(ArpOperation.REPLY)) {
                MacAddress destinationMacAddress = arp.getHeader().getSrcHardwareAddr();
                InetAddress destinationIpAddress = arp.getHeader().getSrcProtocolAddr();

                AttendanceService.addToCache(destinationIpAddress, destinationMacAddress);
            }
        }
    }


}
