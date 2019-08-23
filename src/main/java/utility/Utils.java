/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.Lecture;
import jooq.entities.Tables;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.QueryHints;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.jooq.DSLContext;
import org.jooq.Param;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

/**
 * @author sukhvir
 */
public class Utils {

    private static SessionFactory sessionFactory = null;
    private static final String CODES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~-";
    public static final String UTF8 = "UTF-8";
    public static final String LOAD_ENTITY_HINT = "javax.persistence.loadgraph";
    private static DSLContext jooqContext;

    //creating Session factory on class load
    static {
        try {
            StandardServiceRegistry standardRegistry =
                    new StandardServiceRegistryBuilder()
                            .configure("hibernate.cfg.xml")
                            .build();
            Metadata metaData =
                    new MetadataSources(standardRegistry)
                            .getMetadataBuilder()
                            .build();
            sessionFactory = metaData.getSessionFactoryBuilder()
                    .build();

            jooqContext = DSL.using(SQLDialect.POSTGRES_9_5);

        } catch (Exception e) {
            e.printStackTrace();
        }
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

        return sessionFactory.openSession();
    }


    public static void closeSessionFactory() {
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
     * @param subject  subject of the email id
     * @param message  message of the email ie the body of the email use
     *                 formatedBody to template to decorate the body
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
     * @param regex  The expression to be compared
     */
    public static boolean regexMatch(String regex, String string) {
        return Pattern.compile(regex).matcher(string).find();
    }

    /**
     * does a regular expression matching
     *
     * @param string string to be compared with regex
     * @param regex  The expression to be compared
     * @param flag   Match flags, a bit mask that may include null
     * @return if the regex matches with string
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
    public static String generateSessionId(Session session) {
        String id;

        org.jooq.Query sql = getDsl().select(Tables.USERS.SESSION_ID)
                .from(Tables.USERS)
                .where(Tables.USERS.SESSION_ID.eq("id"))
                .limit(1);

        System.out.println(sql.getSQL());

        do {
            id = generateBase64(20);
            sql.bind(1, id);
        } while (!selectNativeQuery(session, sql).isEmpty());
        return id;
    }

    /**
     * Converts string date to LocalDateTime where the string contains only date
     * and not the time and it set the time to 0:0:0
     *
     * @param date - date in string form
     * @return LocalDateTime of the give date in string format
     */
    public static LocalDateTime getStartDate(String date) {
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
     * @param dateTime to be formated
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

    public static <V> Optional<V> getFromDB(Function<Session, V> todo) {
        Session session = openSession();
        session.beginTransaction();
        V value = null;
        try {
            value = todo.apply(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            //throw new Exception(e);
        } finally {
            session.close();
        }
        return Optional.of(value);
    }

    /**
     * this method check id the student is eligible to mark attendance i.e. if
     * he is within 15 minutes
     */
    public static boolean checkTimeFactorStudentAttendance(LocalDateTime lectureTime, LocalDateTime studentTime) {
        LocalDateTime offset = lectureTime.plusMinutes(15);
        return studentTime.isBefore(offset);
    }

    public static String URLEncode(String string) {
        try {
            return URLEncoder.encode(string, UTF8);
        } catch (Exception e) {
            return "";
        }
    }

    public static String URLDecode(String string) {
        try {
            return URLDecoder.decode(string, UTF8);
        } catch (Exception e) {
            return "";
        }
    }

    public static List<Object[]> selectNativeQuery(Session session, org.jooq.Query theQuery) {
        Query result = session.createNativeQuery(theQuery.getSQL());

        //List<Object> values = theQuery.getBindValues();

        int i = 1;
        for (Param<?> param : theQuery.getParams().values()) {
            if (!param.isInline()) {
                result.setParameter(i++, convertToDatabaseType(param));
            }
        }

        return result.setHint(QueryHints.READ_ONLY, true)
                .getResultList();
    }

    private static <T> Object convertToDatabaseType(Param<T> param) {
        return param.getBinding().converter().to(param.getValue());
    }

    public static <T> List<T> selectNativeQuery(Session session, org.jooq.Query theQuery, Class<T> theClass) {
        org.hibernate.query.Query<T> result = session.createNativeQuery(theQuery.getSQL(), theClass);

        List<Object> values = theQuery.getBindValues();

        for (int i = 0; i < values.size(); i++) {
            result.setParameter(i + 1, values.get(i));
        }

        return result.setHint(QueryHints.READ_ONLY, true)
                .getResultList();
    }


    public static int executeNativeQuery(Session session, org.jooq.Query theQuery) {
        org.hibernate.query.Query result = session.createNativeQuery(theQuery.getSQL());

        List<Object> values = theQuery.getBindValues();

        for (int i = 0; i < values.size(); i++) {
            result.setParameter(i + 1, values.get(i));
        }

        return result.executeUpdate();
    }

    public static boolean isEamil(String email) {
        return regexMatch("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", email, Pattern.CASE_INSENSITIVE);
    }


    public static DSLContext getDsl() {
        return jooqContext;
    }


}
