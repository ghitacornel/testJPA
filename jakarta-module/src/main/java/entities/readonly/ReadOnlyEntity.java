package entities.readonly;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * observe no setters defined
 * best approach is to use a listener and detach it after the load
 *
 * @javax.persistence.PostLoad public void detachAfterLoad(Object o) { entityManager.detach(o); }
 */
@Entity
public class ReadOnlyEntity {

    @Id
    private Integer id;

    private String data;

    public ReadOnlyEntity() {
    }

    public ReadOnlyEntity(Integer id, String data) {
        this.id = id;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public String getData() {
        return data;
    }
}
