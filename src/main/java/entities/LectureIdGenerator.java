package entities;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class LectureIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        if (object instanceof Lecture) {
            Lecture theLecture = (Lecture) object;
            Serializable id = theLecture.getId();

            if (Objects.nonNull(id)) {
                return id;
            } else {
                return getLectureId(Session.class.cast(session));
            }
        }

        return null;
    }

    // this method calls the stored procedure getLectureId to get new lecture id
    public static Serializable getLectureId(Session session) {
        return session.createNamedQuery("getLectureId")
                .getSingleResult().toString();
    }

}
