package lock;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * a simple versioned {@link Entity} is used for optimistic lock
 *
 * @author Cornel
 */
@Entity
public class VersionedEntity {

    @Id
    private Integer id;

    @Basic
    private String name;

    /**
     * applicable only on some certain data types<br>
     * good practice => no getters/setters defined
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
