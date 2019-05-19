/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.attendanceWsService;

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
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author development
 */
public class MacHandlers {

    private static AtomicBoolean isHandlerReady = new AtomicBoolean(false);
    private static PcapNetworkInterface nif;
    private static PcapHandle sendHandle;
    private static PcapHandle receiveHandle;
    private static MacAddress machineMac;
    private static InetAddress machineIp;
    private static Executor executor = Executors.newSingleThreadExecutor();
    private static AtomicBoolean isListnerSet = new AtomicBoolean(false);

    public static String getMachineIp() {

        return Optional.ofNullable(machineIp)
                .map(ip -> ip.toString())
                .orElse("");
    }

    public static String getMachineMac() {
        return Optional.ofNullable(machineMac)
                .map(mac -> mac.toString())
                .orElse("");

    }

    public static String getDetails() {
        try {
            return machineIp.toString() + " | " + machineMac.toString();
        } catch (Exception exception) {
            return "";
        }

    }

    public static boolean isHandlerReady() {
        return isHandlerReady.get();
    }

    /**
     * @param macAddress - delimiter ':'
     */
    public static boolean setHandles(String ipAddress, String macAddress) throws Exception {
        try {
            if (!isHandlerReady.get()) {
                machineIp = InetAddress.getByName(ipAddress);
                machineMac = MacAddress.getByName(macAddress);

                nif = Pcaps.getDevByAddress(machineIp);
                sendHandle = nif.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
                receiveHandle = nif.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
                isHandlerReady.set(true);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            machineIp = null;
            machineMac = null;
            isHandlerReady.set(false);
            e.printStackTrace();
            return false;
        }
    }

    public static void closeHandles() {
        try {
            if (receiveHandle != null && receiveHandle.isOpen()) {
                receiveHandle.breakLoop();
                receiveHandle.close();
            }

            if (sendHandle != null) {
                sendHandle.close();
            }
            isHandlerReady.set(false);
        } catch (NotOpenException ex) {

        }
    }

    public static void setPacketListeners(PacketListener listner) throws Exception {
        try {
            if (isHandlerReady.get() && !isListnerSet.get()) {
                isListnerSet.set(true);
                receiveHandle.loop(-1, listner, executor);
            }
        } catch (Exception exception) {
            throw new Exception();
        }
    }

    public static boolean sendArpPacket(String destinatioIpaddress) {
        try {
            if (isHandlerReady.get()) {
                InetAddress dstAddress = InetAddress.getByName(destinatioIpaddress);
                sendHandle.sendPacket(createSendPacket(dstAddress));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private static Packet createSendPacket(InetAddress destinationIpaddress) {
        ArpPacket.Builder arpBuilder = new ArpPacket.Builder();
        arpBuilder
                .hardwareType(ArpHardwareType.ETHERNET)
                .protocolType(EtherType.IPV4)
                .hardwareAddrLength((byte) MacAddress.SIZE_IN_BYTES)
                .protocolAddrLength((byte) ByteArrays.INET4_ADDRESS_SIZE_IN_BYTES)
                .operation(ArpOperation.REQUEST)
                .srcHardwareAddr(machineMac)
                .srcProtocolAddr(machineIp)
                .dstHardwareAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
                .dstProtocolAddr(destinationIpaddress);

        EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
        etherBuilder.dstAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
                .srcAddr(machineMac)
                .type(EtherType.ARP)
                .payloadBuilder(arpBuilder)
                .paddingAtBuild(true);
        return etherBuilder.build();
    }
}
