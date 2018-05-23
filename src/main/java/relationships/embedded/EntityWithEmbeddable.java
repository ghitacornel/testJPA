package relationships.embedded;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntityWithEmbeddable {

    @Id
    private Integer id;

    @Embedded
    private EmbeddableBean embedded;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EmbeddableBean getEmbedded() {
        return embedded;
    }

    public void setEmbedded(EmbeddableBean embeddable) {
        this.embedded = embeddable;
    }

}
