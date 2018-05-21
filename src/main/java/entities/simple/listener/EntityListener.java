package entities.simple.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class EntityListener {

    @PrePersist
    public void prePersist(EntityWithListener entity) {
        entity.setPrePersist("prePersist");
    }

    @PreUpdate
    public void preUpdate(EntityWithListener entity) {
        entity.setPreUpdate("preUpdate");
    }

    // and check for many other hook methods

}
