package entities;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "teacher_department_link")
@Immutable
public class TeacherDepartmentLink implements Serializable {

    @EmbeddedId
    @Getter
    @NotNull
    private Id id = new Id();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_fid", updatable = false, insertable = false,
            foreignKey = @ForeignKey(name = "teacher_department_link_teacher_foreign_key")
    )
    @Getter
    @NotNull
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_fid", updatable = false, insertable = false,
            foreignKey = @ForeignKey(name = "teacher_department_link_department_foreign_key"))
    @Getter
    @NotNull
    private Department department;

    protected TeacherDepartmentLink() {
    }

    public TeacherDepartmentLink(Teacher theTeacher, Department theDepartment) {
        this.id = new Id(theTeacher.getId(), theDepartment.getId());
        this.teacher = theTeacher;
        this.department = theDepartment;
        theDepartment.getTeachers().add(this);

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherDepartmentLink that = (TeacherDepartmentLink) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Embeddable
    public static class Id implements Serializable {

        @Column(name = "teacher_fid")
        private UUID teacherId;

        @Column(name = "department_fid")
        private Long departmentId;

        protected Id() {
        }

        public Id(UUID theTeacherId, Long theDepartmentId) {
            this.teacherId = theTeacherId;
            this.departmentId = theDepartmentId;
        }

        public boolean equals(Object o) {
            if (o != null && o instanceof Id) {
                Id that = (Id) o;
                return this.teacherId.equals(that.teacherId)
                        && this.departmentId.equals(that.departmentId);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(teacherId, departmentId);
        }

    }
}

