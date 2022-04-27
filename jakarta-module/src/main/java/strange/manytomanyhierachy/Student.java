package strange.manytomanyhierachy;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "studenti")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tip")
public class Student {

    @ManyToMany
    @JoinTable(
            name = "TEACHER_STUDENT",
            joinColumns = {@JoinColumn(name = "id_student", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_teacher", referencedColumnName = "id")},
            uniqueConstraints = {@UniqueConstraint(name = "UK_TS_OK", columnNames = {"id_teacher", "id_student"})}
    )
    final private List<Teacher> teachers = new ArrayList<>();
    @Id
    private Integer id;
    @Column(name = "tip", insertable = false, updatable = false)
    private String tip;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public String getTip() {
        return tip;
    }
}
