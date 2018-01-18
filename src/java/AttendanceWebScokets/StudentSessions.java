/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceWebScokets;

import com.sun.istack.internal.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;

/**
 *
 * @author development
 */
public class StudentSessions {

    private static Map<Session, StudentWsInfo> sessions = new ConcurrentHashMap<>();

    public static void addSession(@NotNull Session session, StudentWsInfo info) {
        sessions.put(session, info);
    }

    public static void removeSession(@NotNull Session session) {
        sessions.remove(session);
    }

    public static StudentWsInfo getStudentWsInfo(Session session) {
        return sessions.get(session);
    }

    public static Session getWsSession(StudentWsInfo info) {
        return sessions.entrySet()
                .parallelStream()
                .filter(entry -> entry.getValue().equals(info))
                .findFirst()
                .map(entry -> entry.getKey())
                .orElse(null);
    }

}
