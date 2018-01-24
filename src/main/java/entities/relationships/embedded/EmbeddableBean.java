package entities.relationships.embedded;

import javax.persistence.*;
import java.util.Date;

/**
 * an {@link Embeddable} is not considered to be an {@link Entity}<br>
 * {@link Embeddable} do not have an {@link Id}
 *
 * @author Cornel
 */
@Embeddable
public class EmbeddableBean {

    @Basic
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_creare")
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
