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
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import utility.BCrypt;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "login", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email", "username"})})
public class Login implements Serializable {

    @Id
    @Column(name = "username")
    @Getter @Setter
    private String username;

    @Column(name = "password")
    @Getter(value = AccessLevel.PRIVATE)
    private String password;

    @Column(name = "type")
    @Getter
    private String type;

    @Column(name = "ids")
    @Getter @Setter
    private int id;

    @Column(name = "email")
    @Getter @Setter
    private String email;

    @Column(name = "token")
    @Getter @Setter
    private String token;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date date;

    @Column(name = "used")
    @Getter @Setter
    private boolean used;

    @Column(name = "session_id")
    @Getter @Setter
    private String sessionId;

    @Column(name = "session_token")
    private String sessionToken;

    @Column(name = "admin_type")
    @Getter
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

    public void setSessionToken(String sessionToken) {
        this.sessionToken = Utils.hash(sessionToken);
    }

    /**
     * this method matches the given session token with the stored session token
     */
    public boolean matchSessionToken(String token) {
        return Utils.hashEquals(this.sessionToken, Utils.hash(token));
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

    public void setType(UserType type) {
        this.type = type.toString();
    }

    public void setAdminType(AdminType adminType) {
        this.adminType = adminType.toString();
    }

}
