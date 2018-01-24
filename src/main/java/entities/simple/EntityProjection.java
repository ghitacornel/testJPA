package entities.simple;

/**
 * an entity projection is not a JPA entity
 */
public class EntityProjection {

    private Integer id;

    private String name;

    public EntityProjection(Integer id, String name) {
        this.id = id;
        this.name = name;
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

}
