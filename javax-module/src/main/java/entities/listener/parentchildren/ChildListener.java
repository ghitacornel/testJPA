package entities.listener.parentchildren;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class ChildListener {

    @PrePersist
    public void prePersist(ChildWithListener entity) {
        entity.setTestColumnOwn("child own set persist");
    }

    @PreUpdate
    public void preUpdate(ChildWithListener entity) {
        entity.setTestColumnOwn("child own set update");
    }

}
