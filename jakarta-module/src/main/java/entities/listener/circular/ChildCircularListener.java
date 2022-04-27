package entities.listener.circular;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class ChildCircularListener {

    @PrePersist
    public void prePersist(ChildCircularWithListener entity) {
        entity.setTestColumnOwn("child own set persist");
        if (entity.getId().equals(1)) {
            entity.getParent().setTestColumnChild1("set by child 1 persist with name " + entity.getName());
        }
        if (entity.getId().equals(2)) {
            entity.getParent().setTestColumnChild2("set by child 2 persist with name " + entity.getName());
        }
    }

    @PreUpdate
    public void preUpdate(ChildCircularWithListener entity) {
        entity.setTestColumnOwn("child own set update");
        if (entity.getId().equals(1)) {
            entity.getParent().setTestColumnChild1("set by child 1 update with name " + entity.getName());
        }
        if (entity.getId().equals(2)) {
            entity.getParent().setTestColumnChild2("set by child 2 update with name " + entity.getName());
        }
    }

}
