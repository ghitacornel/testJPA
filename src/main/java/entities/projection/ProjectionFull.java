package entities.projection;

/**
 * an entity projection is not a JPA entity
 */
public class ProjectionFull extends EntityWithProjection {

    public ProjectionFull(Integer id, String name, Integer value) {
        setId(id);
        setName(name);
        setValue(value);
    }
}
