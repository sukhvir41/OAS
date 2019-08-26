/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import utility.BCrypt;
import utility.Utils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * @author sukhvir
 */
@Entity(name = "User")
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_user_username_email",
                        columnNames = {"username", "email"}
                ),
                @UniqueConstraint(
                        name = "unique_user_username",
                        columnNames = {"username"}
                ),
                @UniqueConstraint(
                        name = "unique_user_email",
                        columnNames = {"email"}
                )
        },
        indexes = {
                @Index(name = "user_username_index", columnList = "username"),
                @Index(name = "user_email_index", columnList = "email"),
                @Index(name = "user_sessionId_index", columnList = "session_Id"),
                @Index(name = "user_token_index", columnList = "token")
        }
)
public final class User implements Serializable {
    @Id
    @Column
    @Getter
    @Setter
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;


    @Column(name = "username", nullable = false, length = 40)
    @Getter
    @Setter
    @NotNull
    @Size(min = 8, max = 40)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    @Getter(value = AccessLevel.PRIVATE)
    @Size(min = 8, max = 100)
    private String password;

    @Column(name = "email", nullable = false, length = 40)
    @Getter
    @Setter
    @NotNull
    @Size(min = 8, max = 40)
    private String email;

    @Column(name = "number", nullable = false)
    @Getter
    @Setter
    @NotNull
    private long number;

    @Column(name = "used")
    @Getter
    @Setter
    private boolean used = true; // used to check if the forget password is used or not

    @Column(name = "token")
    @Setter
    private String token; // used for forget password

    @Column(name = "forgot_password_expiry_date")
    @Setter
    private LocalDateTime forgotPasswordExpiryDate; // used check the forget password token expiry date

    @Column(name = "session_id", length = 50)
    @Setter
    private String sessionId; //used for cookie login

    @Column(name = "session_token")
    private String sessionToken; // used for cookie login

    @Column(name = "session_expiry_date")
    @Setter
    private LocalDateTime sessionExpiryDate; // session cookie expiration date

    @Column(name = "user_type", nullable = false, updatable = false)
    @NotNull
    private String userType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull
    @Getter
    private UserStatus status;

    public User() {
    }

    public static User createdBlockedUser(String username, String password, String email, long number, UserType userType) {
        return new User(username, password, email, number, userType, UserStatus.SUSPENDED);
    }

    public static User createdActiveUser(String username, String password, String email, long number, UserType userType) {
        return new User(username, password, email, number, userType, UserStatus.ACTIVE);
    }

    public User(String username, String password, String email, long number, UserType userType, UserStatus status) {
        this.username = username;
        this.setPassword(password);
        this.email = email;
        this.number = number;
        this.userType = userType.toString();
        this.status = status;
    }

    public Optional<String> getToken() {
        return Optional.ofNullable(this.token);
    }

    public Optional<String> getSessionId() {
        return Optional.ofNullable(this.sessionId);
    }

    public Optional<String> getSessionToken() {
        return Optional.ofNullable(this.sessionToken);
    }

    public Optional<LocalDateTime> getForgotPasswordExpiryDate() {
        return Optional.of(forgotPasswordExpiryDate);
    }

    final public void setSessionToken(String sessionToken) {
        this.sessionToken = Utils.hash(sessionToken);
    }

    public final Optional<LocalDateTime> getSessionExpiryDate() {
        return Optional.ofNullable(sessionExpiryDate);
    }

    /**
     * checks the logged in session is valid or not
     *
     * @param sessionToken the session token from the cookie
     * @param now          the current time of the system
     * @return true if the session token matches and the now is with in ten days else false
     */
    public final boolean isSessionValid(String sessionToken, LocalDateTime now) {
        return getSessionToken()
                .map(s -> Utils.hashEquals(s, Utils.hash(sessionToken)))
                .orElse(false)
                &&
                getSessionExpiryDate()
                        .map(expiryDate -> expiryDate.isAfter(now))
                        .orElse(false);
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
