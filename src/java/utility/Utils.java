/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.Lecture;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

    private static SessionFactory sessionFactory;
    private static String username;
    private static String password;
    private static final String CODES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

//    static {
//        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
//                .configure("hibernate.cfg.xml")
//                .build();
//        Metadata metadata = new MetadataSources(standardRegistry)
//                .getMetadataBuilder()
//                .build();
//        sessionFactory = metadata.getSessionFactoryBuilder().build();
//    }
    static {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("login.txt"));
            username = reader.readLine();
            password = reader.readLine();
        } catch (Exception e) {
            System.out.println("error occured");
        }
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
        try {
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
        try {
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
            transport.connect("smtp.gmail.com", "oasservice.mail", "admin@oas");
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getLectureId() {
        Session session = openSession();

        Lecture lecture = (Lecture) session.get(Lecture.class, generateBase64());
        while (lecture != null) {
            lecture = (Lecture) session.get(Lecture.class, generateBase64());
        }
        session.close();
        return lecture.getId();
    }

    private static String generateBase64() {
        SecureRandom random = new SecureRandom();
        StringBuilder number = new StringBuilder();

        Stream.iterate(0, e -> e + 1)
                .limit(8)
                .forEach((e) -> number.append(CODES.charAt(random.nextInt(63))));

        return number.toString();
    }

    //todo: have to make the token of variable legth but between limits 
    public static String createToken() {
        StringBuilder token = new StringBuilder();
        SecureRandom random = new SecureRandom();
        Stream.iterate(0, e -> e + 1)
                .limit(50)
                .forEach((e) -> token.append(CODES.charAt(random.nextInt(63))));

        return token.toString();

    }

    //Todo: fromated body to match the template
    public static String formatedBody(String message) {
        return message;
    }

    public static boolean regexMatch(String regex, String string) {
        return Pattern.compile(regex).matcher(string).matches();
    }
}
