/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.ArpHardwareType;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.MacAddress;

/**
 *
 * @author sukhvir
 */
public class NewMacaddress {

    private static ConcurrentHashMap<String, String> map;
    private static ExecutorService packetService;
    private static ExecutorService removeService;
    private static MacAddress sourceMacAddress;
    private static InetAddress sourceIpAddress;
    private static PcapNetworkInterface nif;
    private static ReadWriteLock readwrite = new ReentrantReadWriteLock();
    private static PacketListener listener;

    @Getter
    private static String stringSourceIpAddress;
    @Getter
    private static String stringSourceMacAddress;

    public NewMacaddress() throws InstantiationException {
        throw new InstantiationException();
    }

    public static boolean setAddresses(String macAddress, String ipAddress) {
        try {
            readwrite.writeLock().lock();
            sourceMacAddress = MacAddress.getByName(macAddress);

            sourceIpAddress = InetAddress.getByName(ipAddress);
            nif = Pcaps.getDevByAddress(sourceIpAddress);
            readwrite.writeLock().unlock();
            if (nif != null) {
                System.out.println("nif set");
                stringSourceIpAddress = ipAddress;
                stringSourceMacAddress = macAddress;
                packetService = Executors.newFixedThreadPool(10, new MyThreadFactory(nif));
                removeService = Executors.newSingleThreadExecutor();
                map = new ConcurrentHashMap<>();
                addListner();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            readwrite.writeLock().unlock();
            return false;
        }
    }

    private static void addListner() {
        listener = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                if (packet.contains(ArpPacket.class)) {
                    ArpPacket arp = packet.get(ArpPacket.class);
                    if (arp.getHeader().getOperation().equals(ArpOperation.REPLY)) {
                        String destinationMacAddress = arp.getHeader().getSrcHardwareAddr().toString();
                        String destinationIpAddress = arp.getHeader().getSrcProtocolAddr().toString();
                        map.put(destinationIpAddress, destinationMacAddress);
                    }
                }
            }
        };
    }

    private static Runnable createPacketSender(Packet packet) {
        MyRunnable runnable = new MyRunnable(packet, listener);
        return runnable;
    }

    private static Packet createPacket(String dstIpaddress) throws Exception {
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
                .dstProtocolAddr(InetAddress.getByName(dstIpaddress));

        EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
        etherBuilder.dstAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
                .srcAddr(sourceMacAddress)
                .type(EtherType.ARP)
                .payloadBuilder(arpBuilder)
                .paddingAtBuild(true);
        Packet packet = etherBuilder.build();
        return packet;
    }

    public static String getMacAddress(String dstIpaddress) throws Exception {
        System.out.println("Method called");
        if (nif != null) {
            System.out.println("called for mac");
            Runnable runnable = createPacketSender(createPacket(dstIpaddress));
            Future future = packetService.submit(runnable);
            try {
                future.get(2, TimeUnit.SECONDS);
                String macADddress = map.get("/" + dstIpaddress);
                removeEntry(dstIpaddress);
                if (macADddress != null) {
                    return macADddress;
                } else {
                    return "";
                }
            } catch (TimeoutException ex) {
                future.cancel(true);
                //System.out.println("exceptio of tim e /./././././././.././");
                ((MyRunnable) runnable).stop();
                return "";
            }
        } else {
            System.out.println("called when null");
            return "";
        }
    }

    public static void stop() {
        try {
            packetService.shutdown();
            removeService.shutdown();
            map.clear();
        } catch (Exception e) {
        }
    }

    private static void removeEntry(String dstIpaddress) {
        Runnable runnable = () -> map.remove("/" + dstIpaddress);
        removeService.submit(runnable);
    }
}

class MyThreadFactory implements ThreadFactory {

    PcapNetworkInterface nif;

    MyThreadFactory(PcapNetworkInterface theNif) {
        nif = theNif;
    }

    @Override
    public Thread newThread(Runnable r) {
        try {
            PcapHandle handle1 = nif.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
            PcapHandle handle2 = nif.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
            return new MyThread(r, handle1, handle2);
        } catch (Exception e) {
            return null;
        }
    }
}

class MyThread extends Thread {

    @Getter
    PcapHandle sendHandle;
    @Getter
    PcapHandle receiveHandle;

    MyThread(Runnable r, PcapHandle handle1, PcapHandle handle2) {
        super(r);
        sendHandle = handle1;
        receiveHandle = handle2;
    }

    public void sendPacket(Packet packet) throws Exception {
        sendHandle.sendPacket(packet);
    }

    public void receivePacket(PacketListener listner) throws Exception {
        receiveHandle.loop(1, listner);
    }

    public void stopLoop() {
        if (receiveHandle.isOpen()) {
            try {
                receiveHandle.breakLoop();
            } catch (NotOpenException ex) {
            }
        }
    }

    @Override
    protected void finalize() {
        try {
            if (receiveHandle.isOpen()) {
                receiveHandle.close();
            }
            if (sendHandle.isOpen()) {
                sendHandle.close();
            }
        } catch (Exception e) {
        }
    }
}

class MyRunnable implements Runnable {

    private Packet packet;
    private PacketListener listener;
    private MyThread thread;
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private boolean run;
    private AtomicBoolean bool;

    MyRunnable(Packet packet, PacketListener listener) {
        this.packet = packet;
        this.listener = listener;
        bool = new AtomicBoolean(Boolean.TRUE);
    }

    @Override
    public void run() {
        try {
            if (bool.get()) {
                this.thread = (MyThread) Thread.currentThread();
                thread.sendPacket(packet);
                thread.getReceiveHandle().setFilter("arp and dst host " + NewMacaddress.getStringSourceIpAddress(), BpfProgram.BpfCompileMode.OPTIMIZE);
                thread.receivePacket(listener);

            }
        } catch (Exception e) {
        }
    }

    public void stop() {
        if (this.thread != null) {
            thread.stopLoop();
        } else {
            bool.set(Boolean.FALSE);
        }
    }
}
