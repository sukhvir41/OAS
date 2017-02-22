/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import utility.BCrypt;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "login")
public class Login implements Serializable {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "type")
    private String type;

    @Column(name = "ids")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "token")
    private String token;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "used")
    private boolean used;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "session_token")
    private String sessionToken;

    @Column(name = "admin_type")
    private String adminType;

    private Login() {
    }

    public static Login createStudentLogin(String Username, String passsword, int studentId, String email) {
        return new Login(Username, passsword, UserType.Student, studentId, email);
    }

    public static Login createTeacherLogin(String Username, String passsword, int teacherId, String email) {
        return new Login(Username, passsword, UserType.Teacher, teacherId, email);
    }

    public static Login createAdminLogin(String Username, String passsword, String email, AdminType adminType) {
        Login admin = new Login(Username, passsword, UserType.Admin, 0, email);
        admin.setAdminType(adminType);
        return admin;
    }

    private Login(String username, String password, UserType type, int id, String email) {
        this.username = username;
        setPassword(password);
        this.type = type.toString();
        this.id = id;
        this.email = email;
    }

    private Login(String username, String password, UserType type, int id, String email, String token, Date date) {
        this.username = username;
        setPassword(password);
        this.type = type.toString();
        this.id = id;
        this.email = email;
        this.token = token;
        this.date = date;

    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = Utils.hash(sessionToken);
    }

    /**
     * this method matches the given session token with the stored session token
     */
    public boolean matchSessionToken(String token) {
        return Utils.hashEquals(this.sessionToken, Utils.hash(token));
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * this method checks the given password matches with the stored password
     */
    public final boolean checkPassword(String passwordPlainText) {
        return BCrypt.checkpw(passwordPlainText, this.password);
    }

    /**
     * this method hashes the password and sets it
     */
    final public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    private String getPassword() {
        return password;
    }

    private String getSessionToken() {
        return sessionToken;
    }

    public String getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAdminType() {
        return adminType;
    }

    public void setAdminType(AdminType adminType) {
        this.adminType = adminType.toString();
    }

}
