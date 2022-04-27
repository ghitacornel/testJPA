package relationships.collections.maps.onetomany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ChildMap {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private ParentMap parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParentMap getParent() {
        return parent;
    }

    public void setParent(ParentMap parent) {
        this.parent = parent;
    }

}
