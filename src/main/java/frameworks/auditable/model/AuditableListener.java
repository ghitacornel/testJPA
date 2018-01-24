package frameworks.auditable.model;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * The listener must be public
 * Created by Cornel on 10.07.2015.
 */
public class AuditableListener {

    private static String getActualUser() {
        return "the actual logged user as taken from a service";
    }

    @PrePersist
    public void prePersist(Auditable auditable) {
        auditable.creationDate = new Date();
        auditable.creator = getActualUser();
        auditable.lastUpdateDate = null;
        auditable.updater = null;
    }

    @PreUpdate
    public void preUpdate(Auditable auditable) {
        auditable.lastUpdateDate = new Date();
        auditable.updater = getActualUser();
    }

    @PreRemove
    public void preRemove(Auditable auditable) {
        // USUALLY auditable entities must not be removed but marked as removed using a flag
        throw new RuntimeException("this exception will roll back the current transaction thus preventing accidental delete");
    }
}
