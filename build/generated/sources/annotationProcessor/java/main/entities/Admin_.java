package entities;

import java.util.UUID;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Admin.class)
public abstract class Admin_ {

	public static volatile SingularAttribute<Admin, UUID> id;
	public static volatile SingularAttribute<Admin, String> type;
	public static volatile SingularAttribute<Admin, User> user;

	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String USER = "user";

}

