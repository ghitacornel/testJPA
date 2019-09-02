package entities.listener;

import javax.persistence.*;

@Entity
@EntityListeners(EntityListener.class)
public class EntityWithListener {

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
