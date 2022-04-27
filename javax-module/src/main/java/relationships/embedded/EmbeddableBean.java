package relationships.embedded;

import javax.persistence.*;
import java.util.Date;

/**
 * an {@link Embeddable} is not considered to be an {@link Entity}<br>
 * {@link Embeddable} do not have an {@link Id}<br>
 * can use {@link Embeddable} for composite primary keys or foreign keys
 */
@Embeddable
public class EmbeddableBean {

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
