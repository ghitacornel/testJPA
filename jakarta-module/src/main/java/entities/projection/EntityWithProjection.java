package entities.projection;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * entity for which multiple projections were designed and used
 */
@Entity
public class EntityWithProjection {

    @Id
    private Integer id;

    private String name;
    private Integer value;

    public EntityWithProjection() {
        // JPA requires at least 1 no argument constructor with visibility at least default
    }

    public EntityWithProjection(Integer id, String name, Integer value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

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
