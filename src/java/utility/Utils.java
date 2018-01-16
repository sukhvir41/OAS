/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.Lecture;
import entities.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    private static ThreadLocal<Session> sessions = new ThreadLocal<>(); // stores session for each thread

    //creating Session factory on class load
    static {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
        sb.applySettings(cfg.getProperties());
        StandardServiceRegistry standardServiceRegistry = sb.build();
        sessionFactory = cfg.buildSessionFactory(standardServiceRegistry);

    }

    private Utils() throws InstantiationException {
        throw new InstantiationException("don't create an instance of this class");
    }

    /**
     * this methods is used to create a session for hibernate
     *
     * @return Session object of hibernate
     */
    public static Session openSession() {
        if (sessionFactory == null) {
            throw new NullPointerException();
        }

        if (sessions.get() == null) {
            Session session = sessionFactory.openSession();
            sessions.set(session);
        }
        return sessions.get();
    }

    public static void closeSession() {
        if (sessions.get() != null) {
            sessions.get().close();
            sessions.remove();
        }
    }

    public static void closeSesssioFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("session factory closed!!!!!!!!!!!!!!!!!!!!!!!!!");
            sessionFactory = null;
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
     *
     * @param session - hibernate session to do transaction
     * @return Session id for the lecture
     */
    public static String getLectureId(Session session) {

        String Id;
        Lecture lecture;
        do {
            Id = generateBase64();
            lecture = (Lecture) session.get(Lecture.class, Id);
        } while (lecture != null);
        return Id;
    }

    /**
     * this generates a base64 number of 8 digits long
     *
     * @return base64 number
     */
    public static String generateBase64() {
        return generateBase64(8);
    }

    /**
     * this generates a base64 number of the given limit
     *
     * @param limit - the digits limit
     * @return base64 number
     */
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
        return createToken(50);
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
    @Deprecated
    public static String formatedBody(String message) {
        return message;
    }

    /**
     * does a regular expression matching but case sensitive
     *
     * @param string string to be compared with regex
     *
     * @param regex The expression to be compared
     */
    public static boolean regexMatch(String regex, String string) {
        return Pattern.compile(regex).matcher(string).find();
    }

    /**
     * does a regular expression matching
     *
     * @param string string to be compared with regex
     *
     * @param regex The expression to be compared
     *
     * @param flags Match flags, a bit mask that may include null null null null
     * null null null null null null null null null null null null null null
     * null null null null null null null null null null null null null null
     * null null null null null null null null null null null null null null null     {@link #CASE_INSENSITIVE}, {@link #MULTILINE}, {@link #DOTALL},
     *         {@link #UNICODE_CASE}, {@link #CANON_EQ}, {@link #UNIX_LINES},
     *         {@link #LITERAL}, {@link #UNICODE_CHARACTER_CLASS} and {@link #COMMENTS}
     *
     * @return if the regex matches with string
     *
     */
    public static boolean regexMatch(String regex, String string, int flag) {
        return Pattern.compile(regex, flag).matcher(string).find();
    }

    /**
     * this method hashes the given string using SHA-256 and returns a base64
     * string in case of ant error returns an empty string
     *
     * @return the base 64 string
     */
    public static String hash(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] h = md.digest(string.getBytes());
            return Base64.getEncoder().encodeToString(h);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * this method does a slow hash check of the given two has strings
     *
     * @param hash1 - the first hash string
     * @param hash2 = the second hash string
     * @return true if they match else false
     */
    public static boolean hashEquals(String hash1, String hash2) {
        byte[] hashBytes = hash1.getBytes();
        byte[] plainBytes = hash2.getBytes();
        int diff = hashBytes.length ^ plainBytes.length;
        for (int i = 0; i < hashBytes.length && i < plainBytes.length; i++) {
            diff |= hashBytes[i] ^ plainBytes[i];
        }
        return diff == 0;
    }

    /**
     * this method generate a unique session_id for the cookie
     *
     * @return the session id for the cookie
     */
    public static String generateSessionId() {
        Session session = Utils.openSession();
        session.beginTransaction();
        String id;
        List<User> users;
        Query query = session.createQuery("from User where sessionId = :id");
        do {
            id = generateBase64(12);
            query.setString("id", id);
            users = query.list();
        } while (!users.isEmpty());
        session.getTransaction().commit();
        Utils.closeSession();
        return id;
    }

    /**
     * Converts string date to LocalDateTime where the string contains only date
     * and not the time and it set the time to 0:0:0
     *
     * @param date - date in string form
     * @return LocalDateTime of the give date in string format
     */
    public static LocalDateTime getStartdate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalTime localTime = LocalTime.parse("00:00:00");
        return LocalDateTime.of(localDate, localTime);

    }

    /**
     * Converts string date to LocalDateTime where the string contains only date
     * and not the time and it sets the time to 23:59:59
     *
     * @param date - date in string form
     * @return LocalDateTime of the give date in string format
     */
    public static LocalDateTime getEndDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalTime localTime = LocalTime.parse("23:59:59");
        return LocalDateTime.of(localDate, localTime);
    }

    /**
     * formats the date time to dd/MM/yyyy hh:mm
     *
     * @param date time to be formated
     * @return formated date time
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy  kk:mm"));
    }

    /**
     * this methods is for doing stuff in database and handle losing connection
     * and commit data and error handling
     */
    public static void doInDB(Consumer<Session> todo, Consumer<Exception> error) {
        Session session = openSession();
        session.beginTransaction();
        try {
            todo.accept(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            error.accept(e);
        } finally {
            session.close();
        }

    }

    public static <V> Optional<V> getFromDB(Function<Session, V> todo, Consumer<Exception> error) {
        Session session = openSession();
        session.beginTransaction();
        V value = null;
        try {
            value = todo.apply(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            error.accept(e);
        } finally {
            session.close();
        }
        return Optional.of(value);
    }
    
     public static <V> Optional<V> getFromDbDB(Function<Session, V> todo) throws Exception{
        Session session = openSession();
        session.beginTransaction();
        V value = null;
        try {
            value = todo.apply(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return Optional.of(value);
    }
}
