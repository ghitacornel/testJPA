package frameworks.versionable;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * The listener must be public
 * Created by Cornel on 10.07.2015.
 */
public class VersionListener {

    @PrePersist
    public void prePersist(EntityVersion version) {
        version.startDate = new Date();
        version.endDate = new Date();
    }

    @PreUpdate
    public void preUpdate(EntityVersion version) {
        version.endDate = new Date();
        // TODO prevent update of any other fields
    }

    @PreRemove
    public void preRemove(EntityVersion version) {
        // versions of an entity must not be deleted
        throw new RuntimeException("this exception will roll back the current transaction thus preventing accidental delete");
    }
}
