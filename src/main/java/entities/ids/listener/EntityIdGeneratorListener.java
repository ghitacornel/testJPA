package entities.ids.listener;

import javax.persistence.PrePersist;
import java.util.UUID;

/**
 * this listener will set an id by hand
 */
public class EntityIdGeneratorListener {

    @PrePersist
    public void prePersist(EntityWithIdListener entitate) {
        entitate.id = UUID.randomUUID().toString();
    }

}
