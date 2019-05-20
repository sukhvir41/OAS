package utility;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.type.PostgresUUIDType;

import java.sql.Types;

public class MyPostgresDialet extends PostgreSQL95Dialect {

	public MyPostgresDialet() {
		super();
		this.registerHibernateType( Types.OTHER, PostgresUUIDType.class.getName() );
	}
}
