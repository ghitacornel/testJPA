package queries.simple;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "SQE")// specify and use a shorter entity name for writing shorter JPQL queries
public class SimpleQueryEntity {

    @Id
    private Integer id;

    @Basic(optional = false)
    private String name;

    private Integer value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
