package entities;

import entities.SubjectClassRoomLink.Id;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SubjectClassRoomLink.class)
public abstract class SubjectClassRoomLink_ {

	public static volatile SingularAttribute<SubjectClassRoomLink, Subject> subject;
	public static volatile SingularAttribute<SubjectClassRoomLink, ClassRoom> classRoom;
	public static volatile SingularAttribute<SubjectClassRoomLink, Id> id;

	public static final String SUBJECT = "subject";
	public static final String CLASS_ROOM = "classRoom";
	public static final String ID = "id";

}

