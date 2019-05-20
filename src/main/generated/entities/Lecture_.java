package entities;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Lecture.class)
public abstract class Lecture_ {

	public static volatile SingularAttribute<Lecture, LocalDateTime> date;
	public static volatile SingularAttribute<Lecture, Integer> count;
	public static volatile SingularAttribute<Lecture, Boolean> ended;
	public static volatile SingularAttribute<Lecture, Teaching> teaching;
	public static volatile SingularAttribute<Lecture, String> id;
	public static volatile ListAttribute<Lecture, Attendance> attendances;

	public static final String DATE = "date";
	public static final String COUNT = "count";
	public static final String ENDED = "ended";
	public static final String TEACHING = "teaching";
	public static final String ID = "id";
	public static final String ATTENDANCES = "attendances";

}

