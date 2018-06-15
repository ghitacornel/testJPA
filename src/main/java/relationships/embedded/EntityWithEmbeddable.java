package relationships.embedded;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EntityWithEmbeddable {

    @Id
    private Integer id;

    /**
     * simple embeddable usage
     */
    @Embedded
    private EmbeddableBean embedded;

    /**
     * primitive embeddable usage
     */
    @ElementCollection
    @JoinTable(name = "EWE_Names")// can override default table and foreign keys
    final private List<String> names = new ArrayList<>();

    /**
     * collection of embeddable usage
     */
    @ElementCollection
    final private List<EmbeddableBean> relatedEmbedded = new ArrayList<>();

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

    public List<String> getNames() {
        return names;
    }

    public List<EmbeddableBean> getRelatedEmbedded() {
        return relatedEmbedded;
    }
}
