package relationships.embedded;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EntityWithEmbeddable {

    @Id
    private Integer id;

    @Embedded
    private EmbeddableBean embedded;

    @ElementCollection
    private List<String> names = new ArrayList<>();

    @ElementCollection
    private List<EmbeddableBean> relatedEmbedded = new ArrayList<>();

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
