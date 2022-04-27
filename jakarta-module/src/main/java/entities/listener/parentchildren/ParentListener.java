package entities.listener.parentchildren;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class ParentListener {

    @PrePersist
    public void prePersist(ParentWithListener entity) {
        entity.setTestColumnOwn("parent own set persist");
        entity.getChildren().forEach(child -> child.setTestColumnOther("parent persist"));
    }

    @PreUpdate
    public void preUpdate(ParentWithListener entity) {
        entity.setTestColumnOwn("parent own set update");
        entity.getChildren().forEach(child -> child.setTestColumnOther("parent update"));
    }

}
