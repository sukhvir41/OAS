package utility;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.type.PostgresUUIDType;

public class MyPostgresDialet extends PostgreSQL95Dialect {

	public MyPostgresDialet() {
		super();
		this.registerHibernateType( Types.OTHER, PostgresUUIDType.class.getName() );
	}
}
