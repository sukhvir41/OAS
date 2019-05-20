package entities;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BackupData.class)
public abstract class BackupData_ {

	public static volatile SingularAttribute<BackupData, LocalDateTime> date;
	public static volatile SingularAttribute<BackupData, String> data;
	public static volatile SingularAttribute<BackupData, String> name;
	public static volatile SingularAttribute<BackupData, String> classRoom;
	public static volatile SingularAttribute<BackupData, Integer> rollNumber;
	public static volatile SingularAttribute<BackupData, Long> id;
	public static volatile SingularAttribute<BackupData, String> backupName;

	public static final String DATE = "date";
	public static final String DATA = "data";
	public static final String NAME = "name";
	public static final String CLASS_ROOM = "classRoom";
	public static final String ROLL_NUMBER = "rollNumber";
	public static final String ID = "id";
	public static final String BACKUP_NAME = "backupName";

}

