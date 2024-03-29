package entities.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class EntityListener {

    @PrePersist
    public void nameDoesNotMatterPrePersist(EntityWithListener entity) {
        entity.setPrePersist("prePersist");
    }

    @PreUpdate
    public void nameDoesNotMatterPreUpdate(EntityWithListener entity) {
        entity.setPreUpdate("preUpdate");
    }

    // check for other hook methods
    // check for difference between PRE vs POST
    // check similarities and differences between entity listeners and database triggers

}
