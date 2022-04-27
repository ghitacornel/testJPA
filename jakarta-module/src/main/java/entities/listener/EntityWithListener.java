package entities.listener;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
// ENTITY LISTENERS are inherited
public class EntityWithListener extends AbstractEntityWithListener {

    @Id
    private Integer id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String prePersist;

    private String preUpdate;

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

    public String getPrePersist() {
        return prePersist;
    }

    public void setPrePersist(String prePersist) {
        this.prePersist = prePersist;
    }

    public String getPreUpdate() {
        return preUpdate;
    }

    public void setPreUpdate(String preUpdate) {
        this.preUpdate = preUpdate;
    }
}
