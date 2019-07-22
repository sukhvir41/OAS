package entities;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, Boolean> used;
	public static volatile SingularAttribute<User, String> sessionId;
	public static volatile SingularAttribute<User, LocalDateTime> sessionExpiryDate;
	public static volatile SingularAttribute<User, String> token;
	public static volatile SingularAttribute<User, Long> number;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> sessionToken;
	public static volatile SingularAttribute<User, UUID> id;
	public static volatile SingularAttribute<User, String> userType;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, LocalDateTime> forgotPasswordExpiryDate;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, UserStatus> status;

	public static final String USED = "used";
	public static final String SESSION_ID = "sessionId";
	public static final String SESSION_EXPIRY_DATE = "sessionExpiryDate";
	public static final String TOKEN = "token";
	public static final String NUMBER = "number";
	public static final String PASSWORD = "password";
	public static final String SESSION_TOKEN = "sessionToken";
	public static final String ID = "id";
	public static final String USER_TYPE = "userType";
	public static final String EMAIL = "email";
	public static final String FORGOT_PASSWORD_EXPIRY_DATE = "forgotPasswordExpiryDate";
	public static final String USERNAME = "username";
	public static final String STATUS = "status";

}

