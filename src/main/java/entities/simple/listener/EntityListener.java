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

    // TODO check for other hook methods
    // TODO check for difference between PRE vs POST
    // TODO check similarities and differences between entity listeners and database triggers

}
