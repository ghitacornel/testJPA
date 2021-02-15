package strange.manytomanyhierachy;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher {

    @Id
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "TEACHER_STUDENT",
            joinColumns = {@JoinColumn(name = "id_teacher", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_student", referencedColumnName = "id")},
            uniqueConstraints = {@UniqueConstraint(name = "UK_TS_OK", columnNames = {"id_teacher", "id_student"})}
    )
    final private List<Student> students = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "TEACHER_STUDENT",
            joinColumns = {@JoinColumn(name = "id_teacher", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_student", referencedColumnName = "id")},
            uniqueConstraints = {@UniqueConstraint(name = "UK_TS_OK", columnNames = {"id_teacher", "id_student"})}
    )
    @Where(clause = "tip='distanta'")
    final private List<StudentDistanta> studentsDistanta = new ArrayList<>();

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<StudentDistanta> getStudentsDistanta() {
        return studentsDistanta;
    }
}
