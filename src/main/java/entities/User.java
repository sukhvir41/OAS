/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import utility.BCrypt;
import utility.Utils;

/**
 * @author sukhvir
 */
@Entity(name = "User")
@Table(name = "users")
public final class User implements Serializable {


    @Id
    @Column
    @Getter
    @Setter
    @Type(type = "pg-uuid")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;


    @Column(name = "username", unique = true, nullable = false, length = 40)
    @Getter
    @Setter
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    @Getter(value = AccessLevel.PRIVATE)
    private String password;

    @Column(name = "email", unique = true, nullable = false, length = 40)
    @Getter
    @Setter
    private String email;

    @Column(name = "token")
    @Getter
    @Setter
    private String token; // used for forget password

    @Column(name = "number", nullable = false)
    @Getter
    @Setter
    private long number;

    @Column(name = "used")
    @Getter
    @Setter
    private boolean used; // used to check if the forget password is used or not

    @Column(name = "session_id", unique = true, length = 50)
    @Getter
    @Setter
    private String sessionId; //used for cookie login

    @Column(name = "session_token")
    private String sessionToken; // used for cookie login

    @Column(name = "date")
    @Getter
    @Setter
    private LocalDateTime date; // used check the forget password token expiry date

    @Column(name = "user_type", nullable = false)
    @Setter
    private String userType;

    public User() {
    }

    public User(String username, String password, String email, long number, UserType userType) {
        this.username = username;
        this.setPassword(password);
        this.email = email;
        this.number = number;
        this.userType = userType.toString();
    }

    final public void setSessionToken(String sessionToken) {
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
    final public boolean checkPassword(String passwordPlainText) {
        return BCrypt.checkpw(passwordPlainText, this.password);
    }

    /**
     * this method hashes the password and sets it
     */
    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    public UserType getUserType() {
        return UserType.valueOf(userType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
