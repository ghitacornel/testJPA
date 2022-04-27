package entities.projection;

/**
 * an entity projection is not a JPA entity
 */
public class Projection1 {

    final private Integer id;
    final private String name;

    public Projection1(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
