/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import org.pcap4j.core.*;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.ArpHardwareType;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.MacAddress;

import java.net.InetAddress;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author sukhvir
 */
public class MacAddressUtil {

    private static MacAddress sourceMacAddress;
    private static InetAddress sourceIpAddress;
    private static PcapNetworkInterface nif;
    private static String stringSourceIpAddress;
    private static ReadWriteLock readwrite = new ReentrantReadWriteLock();
    private MacAddress destinationMacAddress;
    private PcapHandle handle;
    private PcapHandle sendHandle;
    private int timeOut = 5;

    public static boolean setAddresses(String macAddress, String ipAddress) {

        try {
            readwrite.writeLock().lock();
            sourceMacAddress = MacAddress.getByName(macAddress);
            stringSourceIpAddress = ipAddress;
            sourceIpAddress = InetAddress.getByName(stringSourceIpAddress);
            nif = Pcaps.getDevByAddress(sourceIpAddress);
            readwrite.writeLock().unlock();
            if (nif != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            readwrite.writeLock().unlock();
            return false;
        }
    }

    private String getMacAddressImpl(String destinationIpAddress) {
        handle = null;
        sendHandle = null;
        ExecutorService service = null;
        readwrite.readLock().lock();
        try {
            handle = nif.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
            sendHandle = nif.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
            service = Executors.newSingleThreadExecutor();
            handle.setFilter(
                    "arp and src host " + destinationIpAddress
                            + " and dst host " + stringSourceIpAddress
                            + " and ether dst " + Pcaps.toBpfString(sourceMacAddress),
                    BpfProgram.BpfCompileMode.OPTIMIZE
            );

            PacketListener listener = new PacketListener() {
                @Override
                public void gotPacket(Packet packet) {
                    if (packet.contains(ArpPacket.class)) {
                        ArpPacket arp = packet.get(ArpPacket.class);
                        if (arp.getHeader().getOperation().equals(ArpOperation.REPLY)) {
                            destinationMacAddress = arp.getHeader().getSrcHardwareAddr();
                        }
                    }

                }
            };

            ArpPacket.Builder arpBuilder = new ArpPacket.Builder();
            arpBuilder
                    .hardwareType(ArpHardwareType.ETHERNET)
                    .protocolType(EtherType.IPV4)
                    .hardwareAddrLength((byte) MacAddress.SIZE_IN_BYTES)
                    .protocolAddrLength((byte) ByteArrays.INET4_ADDRESS_SIZE_IN_BYTES)
                    .operation(ArpOperation.REQUEST)
                    .srcHardwareAddr(sourceMacAddress)
                    .srcProtocolAddr(sourceIpAddress)
                    .dstHardwareAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
                    .dstProtocolAddr(InetAddress.getByName(destinationIpAddress));

            EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
            etherBuilder.dstAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
                    .srcAddr(sourceMacAddress)
                    .type(EtherType.ARP)
                    .payloadBuilder(arpBuilder)
                    .paddingAtBuild(true);
            Packet p = etherBuilder.build();
            sendHandle.sendPacket(p);

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        handle.loop(1, listener);
                    } catch (Exception ex) {
                    }
                }
            };

            Future future = service.submit(r);
            future.get(timeOut, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            try {
                handle.breakLoop();
            } catch (Exception exception) {
            }
        } catch (Exception e) {
        } finally {
            try {
                if (handle != null && handle.isOpen()) {
                    handle.close();
                }
                if (sendHandle != null && sendHandle.isOpen()) {
                    sendHandle.close();
                }
                if (service != null && !service.isShutdown()) {
                    service.shutdown();
                }
            } catch (Exception e) {
            }
        }
        readwrite.readLock().unlock();
        if (destinationMacAddress != null) {
            return destinationMacAddress.toString();
        } else {
            return "";
        }
    }

    public String getMacAddress(String destinationIpAddress) {
        if (nif != null) {
            return getMacAddressImpl(destinationIpAddress);
        } else {
            return "";
        }

    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public static String getSourceMacAddress() {
        if (sourceMacAddress != null) {
            return sourceMacAddress.toString();
        } else {
            return "";
        }
    }

    public static String getSourceIpAddress() {
        if (stringSourceIpAddress != null) {
            return stringSourceIpAddress;
        } else {
            return "";
        }
    }

}
