package entities.listener.parentchildren;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class ParentListener {

    @PrePersist
    public void prePersist(ParentWithListener entity) {
        entity.setTestColumnOwn("parent own set persist");
        for (ChildWithListener child : entity.getChildren()) {
            child.setTestColumnOther("parent persist");
        }
    }

    @PreUpdate
    public void preUpdate(ParentWithListener entity) {
        entity.setTestColumnOwn("parent own set update");
        for (ChildWithListener child : entity.getChildren()) {
            child.setTestColumnOther("parent update");
        }
    }

}
