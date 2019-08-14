package utility;

import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.type.PostgresUUIDType;

import java.sql.Types;

public class MyPostgresDialect extends PostgreSQL10Dialect {

    public MyPostgresDialect() {
        super();
        this.registerHibernateType(Types.OTHER, PostgresUUIDType.class.getName());
    }
}
