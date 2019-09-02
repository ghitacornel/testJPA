package lock;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * a simple versioned {@link Entity} is used for optimistic lock
 */
@Entity
public class VersionedEntity {

    @Id
    private Integer id;

    private String name;

    /**
     * @Version is applicable only on some certain data types<br>
     * good practice : do not define getters/setters for @Version marked properties
     */
    @Version
    private int version;

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
