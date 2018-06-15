package entities.projection;

/**
 * an entity projection is not a JPA entity
 */
public class Projection2 {

    public Integer id;

    public Integer value;

    public Projection2(Integer id, Integer value) {
        this.id = id;
        this.value = value;
    }

}
