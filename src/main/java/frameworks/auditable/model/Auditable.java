package frameworks.auditable.model;

import javax.persistence.*;
import java.util.Date;

/**
 * all auditable entitites must extend this abstract class<br>
 * all auditable entities must be persisted in tables that contain these mandatory columns<br>
 * field setters as seen here can be made default accessible
 * Created by Cornel on 10.07.2015.
 */
@MappedSuperclass
@EntityListeners(AuditableListener.class)
public abstract class Auditable {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate", nullable = false)
    Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastUpdatedDate")
    Date lastUpdateDate;

    @Basic(optional = false)
    @Column(name = "creator", nullable = false)
    String creator;

    @Basic(optional = true)
    @Column(name = "updater")
    String updater;

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public String getCreator() {
        return creator;
    }

    public String getUpdater() {
        return updater;
    }

}
