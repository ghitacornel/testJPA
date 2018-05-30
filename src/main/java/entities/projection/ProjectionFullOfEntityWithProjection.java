package entities.projection;

/**
 * an entity projection is not a JPA entity
 */
public class ProjectionFullOfEntityWithProjection extends EntityWithProjection {

    public ProjectionFullOfEntityWithProjection(Integer id, String name, Integer value) {
        setId(id);
        setName(name);
        setValue(value);
    }
}
