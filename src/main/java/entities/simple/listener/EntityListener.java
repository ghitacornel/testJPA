package entities.simple.listener;

import javax.persistence.PrePersist;
import java.util.Date;

public class EntityListener {

    @PrePersist
    public void prePersist(EntityWithListener entitate) {
        entitate.setCreationDate(new Date());
    }

    // and check for many other hook methods

}
