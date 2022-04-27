package entities.listener.circular;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class ParentCircularListener {

    @PrePersist
    public void prePersist(ParentCircularWithListener entity) {
        entity.setTestColumnOwn("parent own set persist");
        entity.getChildren().forEach(child -> child.setTestColumnOther("parent persist"));
    }

    @PreUpdate
    public void preUpdate(ParentCircularWithListener entity) {
        entity.setTestColumnOwn("parent own set update");
        entity.getChildren().forEach(child -> child.setTestColumnOther("parent update"));
    }

}
