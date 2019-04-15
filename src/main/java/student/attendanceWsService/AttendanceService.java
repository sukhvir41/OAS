package student.attendanceWsService;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.pcap4j.util.MacAddress;

import javax.websocket.Session;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

public class AttendanceService {

    private static Map<InetAddress, MacAddress> addressesCache = new ConcurrentHashMap<>();

    private static Map<InetAddress, Session> replyToSend = new ConcurrentHashMap<>();

    private static ForkJoinPool forkJoinPool = new ForkJoinPool(3);

    public static void addToCache(InetAddress ipAddress, MacAddress macAddress) {
        CompletableFuture<ImmutablePair<InetAddress, MacAddress>> future = new CompletableFuture<>();

        future.thenApplyAsync((AttendanceService::addToCacheImpl), forkJoinPool)
                .thenAcceptAsync(pair -> sendReply(pair.left, pair.right), forkJoinPool);

        future.complete(ImmutablePair.of(ipAddress, macAddress));
    }

    private static ImmutablePair<InetAddress, MacAddress> addToCacheImpl(ImmutablePair<InetAddress, MacAddress> pair) {
        addressesCache.put(pair.left, pair.right);
        return pair;
    }

    private static void sendReply(InetAddress ipAddress, MacAddress macAddress) {
        Session theSession = replyToSend.remove(ipAddress);
        if (Objects.nonNull(theSession)) {
            theSession.getAsyncRemote()
                    .sendText("(from not cache )  the mac address is " + macAddress.toString());
        }
    }


    public static void getMac(String ip, Session theSession) {
        CompletableFuture<ImmutablePair<String, Session>> future = new CompletableFuture<>();
        future.thenAcceptAsync((pair -> checkCache(pair.left, pair.right)), forkJoinPool);
        future.complete(new ImmutablePair<>(ip, theSession));
    }

    private static void checkCache(String ip, Session theSession) {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(ip);

            MacAddress macAddress = addressesCache.get(inetAddress);

            if (Objects.isNull(macAddress)) {
                theSession.getAsyncRemote()
                        .sendText(" not in cache  ");
                theSession.getAsyncRemote()
                        .sendText(" arp sent packet status : " + MacHandlers.sendArpPacket(ip));
                replyToSend.put(inetAddress, theSession);
            } else {
                theSession.getAsyncRemote()
                        .sendText("(from cache) the mac address is " + macAddress.toString());
            }
        } catch (UnknownHostException e) {
            theSession.getAsyncRemote()
                    .sendText("(in method check cache ) provided data is not right");
        }
    }


}
