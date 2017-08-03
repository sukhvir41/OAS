/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
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
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email", "username"})})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements Serializable, Visitable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private long id;

    @Column(name = "username")
    @Getter
    @Setter
    private String username;

    @Column(name = "password")
    @Getter(value = AccessLevel.PRIVATE)
    private String password;

    @Column(name = "email")
    @Getter
    @Setter
    private String email;

    @Column(name = "token")
    @Getter
    @Setter
    private String token;

    @Column(name = "number")
    @Getter
    @Setter
    private long number;

    @Column(name = "used")
    @Getter
    @Setter
    private boolean used;

    @Column(name = "sessionid")
    @Getter
    @Setter
    private String sessionId;

    @Column(name = "sessiontoken")
    private String sessionToken;

    @Column(name = "date")
    @Getter
    @Setter
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime date; // used check the token expiry date

    public User() {
    }

    public User(String username, String password, String email, long number) {
        this.username = username;
        this.setPassword(password);
        this.email = email;
        this.number = number;
    }

    final public void setSessionToken(String sessionToken) {
        this.sessionToken = Utils.hash(sessionToken);
    }

    /**
     * this method matches the given session token with the stored session token
     */
    final public boolean matchSessionToken(String token) {
        return Utils.hashEquals(this.sessionToken, Utils.hash(token));
    }

    /**
     * this method checks the given password matches with the stored password
     */
    final public boolean checkPassword(String passwordPlainText) {
        return BCrypt.checkpw(passwordPlainText, this.password);
    }

    /**
     * this method hashes the password and sets it
     */
    final public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    public abstract UserType getUserType();
}
