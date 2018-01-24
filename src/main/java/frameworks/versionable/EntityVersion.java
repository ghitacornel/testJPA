package frameworks.versionable;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(VersionListener.class)
public abstract class EntityVersion {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "startDate", nullable = false)
    Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endDate")
    Date endDate;
    /**
     * version internal specific  data
     */

    @Id
    @SequenceGenerator(name = "versionsSequenceGenerator", sequenceName = "ids")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "versionsSequenceGenerator")
    private Long id;

    public Long getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

}