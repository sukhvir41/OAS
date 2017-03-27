/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.Lecture;
import entities.Login;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.DatatypeConverter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author sukhvir
 */
public class Utils {

    private static SessionFactory sessionFactory = null;
    private static final String CODES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789/=";

    private Utils() throws Exception {
        throw new Exception("dont use this contructor");
    }

    /**
     * this methods is used to create a session for hibernate
     *
     * @return Session object of hibernate
     */
    public static Session openSession() {
        if (sessionFactory == null) {
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
            sb.applySettings(cfg.getProperties());
            StandardServiceRegistry standardServiceRegistry = sb.build();
            sessionFactory = cfg.buildSessionFactory(standardServiceRegistry);
        }
        return sessionFactory.openSession();
    }

    public static void closeSesssioFactory() {
        try {
            sessionFactory.close();
            System.out.println("session factory closed!!!!!!!!!!!!!!!!!!!!!!!!!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * this method sends an email using the email id oasservice.mail@gmail.com
     * to the specified emailids
     *
     * @param emailIds array of email ids(TO)
     * @param subject subject of the email id
     * @param message message of the email ie the body of the email use
     * formatedBody to template to decorate the body
     * @return email sent successfully or not
     * @see #formatedBody(java.lang.String)
     */
    public static boolean sendMail(String[] emailIds, String subject, String message) {
        Properties mailServerProperties;
        javax.mail.Session getMailSession;
        MimeMessage generateMailMessage;
        Transport transport;
        String username, password;
        try {
            File file = new File("C:\\Users\\sukhvir\\Documents\\OAS\\login.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            password = reader.readLine();
            username = reader.readLine();
            mailServerProperties = System.getProperties();
            mailServerProperties.put("mail.smtp.port", "587");
            mailServerProperties.put("mail.smtp.auth", "true");
            mailServerProperties.put("mail.smtp.starttls.enable", "true");

            getMailSession = javax.mail.Session.getDefaultInstance(mailServerProperties, null);
            generateMailMessage = new MimeMessage(getMailSession);

            for (String email : emailIds) {
                generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            }
            generateMailMessage.setSubject(subject);
            generateMailMessage.setContent(message, "text/html");
            transport = getMailSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", username, password);
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendMail(String emailId, String subject, String message) {
        Properties mailServerProperties;
        javax.mail.Session getMailSession;
        MimeMessage generateMailMessage;
        Transport transport;
        String username, password;
        try {
            File file = new File("C:\\Users\\sukhvir\\Documents\\OAS\\login.txt");
            System.out.println(file.exists());
            BufferedReader reader = new BufferedReader(new FileReader(file));
            password = reader.readLine();
            username = reader.readLine();
            mailServerProperties = System.getProperties();
            mailServerProperties.put("mail.smtp.port", "587");
            mailServerProperties.put("mail.smtp.auth", "true");
            mailServerProperties.put("mail.smtp.starttls.enable", "true");

            getMailSession = javax.mail.Session.getDefaultInstance(mailServerProperties, null);
            generateMailMessage = new MimeMessage(getMailSession);

            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailId));

            generateMailMessage.setSubject(subject);
            generateMailMessage.setContent(message, "text/html");
            transport = getMailSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", username, password);
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * this gives a unique lecture id in base64 of 8 characters long
     */
    public static String getLectureId() {
        Session session = openSession();
        session.beginTransaction();
        String Id = generateBase64();
        Lecture lecture = (Lecture) session.get(Lecture.class, Id);
        while (lecture != null) {
            Id = generateBase64();
            lecture = (Lecture) session.get(Lecture.class, Id);
        }
        session.getTransaction().commit();
        session.close();
        return Id;
    }

    public static String generateBase64() {
        SecureRandom random = new SecureRandom();
        StringBuilder number = new StringBuilder();

        Stream.iterate(0, e -> e + 1)
                .limit(8)
                .forEach((e) -> number.append(CODES.charAt(random.nextInt(63))));

        return number.toString();
    }

    private static String generateBase64(int limit) {
        SecureRandom random = new SecureRandom();
        StringBuilder number = new StringBuilder();

        Stream.iterate(0, e -> e + 1)
                .limit(limit)
                .forEach((e) -> number.append(CODES.charAt(random.nextInt(63))));

        return number.toString();
    }

    //todo: have to make the token of variable legth but between limits 
    /**
     * this create a token of length 50 ie random in base64
     *
     * @return
     */
    public static String createToken() {
        StringBuilder token = new StringBuilder();
        SecureRandom random = new SecureRandom();
        Stream.iterate(0, e -> e + 1)
                .limit(50)
                .forEach((e) -> token.append(CODES.charAt(random.nextInt(63))));

        return token.toString();

    }

    public static String createToken(int limit) {
        StringBuilder token = new StringBuilder();
        SecureRandom random = new SecureRandom();
        Stream.iterate(0, e -> e + 1)
                .limit(limit)
                .forEach((e) -> token.append(CODES.charAt(random.nextInt(63))));

        return token.toString();
    }

    //Todo: fromated body to match the template
    public static String formatedBody(String message) {
        return message;
    }

    public static boolean regexMatch(String regex, String string) {
        return Pattern.compile(regex).matcher(string).find();
    }

    public static boolean regexMatch(String regex, String string, int flag) {
        return Pattern.compile(regex, flag).matcher(string).find();
    }

    public static String hash(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] h = md.digest(string.getBytes());
            return DatatypeConverter.printHexBinary(h);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean hashEquals(String hash1, String hash2) {
        byte[] hashBytes = hash1.getBytes();
        byte[] plainBytes = hash2.getBytes();
        int diff = hashBytes.length ^ plainBytes.length;
        for (int i = 0; i < hashBytes.length && i < plainBytes.length; i++) {
            diff |= hashBytes[i] ^ plainBytes[i];
        }
        return diff == 0;
    }

    public static String generateSessionId() {
        Session session = Utils.openSession();
        session.beginTransaction();
        String id = generateBase64(12);
        List<Login> logins;
        Query query = session.createQuery("from Login where sessionId = :id");
        query.setString("id", id);
        logins = query.list();
        while (logins.size() > 0) {
            id = generateBase64(12);
            query.setString("id", id);
            logins = query.list();
        }
        session.getTransaction().commit();
        session.close();
        return id;
    }
}
