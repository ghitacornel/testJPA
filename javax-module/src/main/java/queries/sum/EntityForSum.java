package queries.sum;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class EntityForSum {

    @Id
    private Integer id;

    private Integer salary;

    private Integer age;

    public EntityForSum() {
    }

    public EntityForSum(Integer id, Integer salary, Integer age) {
        this.id = id;
        this.salary = salary;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityForSum that = (EntityForSum) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
