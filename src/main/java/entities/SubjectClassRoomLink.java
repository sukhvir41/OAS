package entities;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "subject_class_link")
@Immutable
public class SubjectClassRoomLink implements Serializable {

    @EmbeddedId
    @Getter
    @NotNull
    private Id id = new Id();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_fid", nullable = false, insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "subject_class_link_subject_foreign_key")
    )
    @Getter
    @NotNull
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_room_fid", nullable = false, insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "subject_class_link_class_foreign_key")
    )
    @Getter
    @NotNull
    private ClassRoom classRoom;

    protected SubjectClassRoomLink() {
    }


    public SubjectClassRoomLink(@NotNull Subject subject, @NotNull ClassRoom classRoom) {
        this.id = new Id(subject.getId(), classRoom.getId());
        this.subject = subject;
        this.classRoom = classRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof SubjectClassRoomLink) {
            SubjectClassRoomLink that = (SubjectClassRoomLink) o;
            return this.id.equals(that.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Embeddable
    public static class Id implements Serializable {

        @Column(name = "subject_fid")
        private Long subjectId;

        @Column(name = "class_room_fid")
        private Long classRoomId;


        protected Id() {
        }

        public Id(Long subjectId, Long classRoomId) {
            this.subjectId = subjectId;
            this.classRoomId = classRoomId;
        }

        public boolean equals(Object o) {
            if (o != null && o instanceof SubjectClassRoomLink.Id) {
                SubjectClassRoomLink.Id that = (SubjectClassRoomLink.Id) o;
                return this.subjectId.equals(that.subjectId)
                        && this.classRoomId.equals(that.classRoomId);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(subjectId, classRoomId);
        }
    }

}
