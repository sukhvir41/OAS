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

    public Login() {
    }

    public Login(String username, String password, String type, int id, String email) {
        this.username = username;
        setPassword(password);
        this.type = type;
        this.id = id;
        this.email = email;
    }

    public Login(String username, String password, String type, int id, String email, String token, Date date) {
        this.username = username;
        setPassword(password);
        this.type = type;
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

    public boolean matchSessionToken(String token) {
        return Utils.hashEquals(this.sessionToken, token);
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

    public boolean checkPassword(String passwordPlainText) {
        return BCrypt.checkpw(passwordPlainText, this.password);
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

}
