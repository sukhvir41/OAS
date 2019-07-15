package entities;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "student_subject_link")
@Immutable
public class StudentSubjectLink implements Serializable {

    @EmbeddedId
    @Getter
    private Id id = new Id();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_fid", nullable = false, insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "student_subject_link_student_foreign_key")
    )
    @NotNull
    @Getter
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_fid", nullable = false, insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "student_subject_link_student_foreign_key")
    )
    @NotNull
    @Getter
    private Subject subject;

    protected StudentSubjectLink() {
    }

    public StudentSubjectLink(Student student, Subject subject) {
        this.student = student;
        this.subject = subject;
        this.id = new Id(student.getId(), subject.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof SubjectClassRoomLink) {
            StudentSubjectLink that = (StudentSubjectLink) o;
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

        @Column(name = "student_fid")
        private UUID studentId;

        @Column(name = "subject_fid")
        private Long subjectId;

        protected Id() {
        }

        public Id(UUID studentId, Long subjectId) {
            this.studentId = studentId;
            this.subjectId = subjectId;
        }

        public boolean equals(Object o) {
            if (o != null && o instanceof StudentSubjectLink.Id) {
                StudentSubjectLink.Id that = (StudentSubjectLink.Id) o;
                return this.subjectId.equals(that.subjectId)
                        && this.studentId.equals(that.studentId);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(subjectId, studentId);
        }

    }
}
