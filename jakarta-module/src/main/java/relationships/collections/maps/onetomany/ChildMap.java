package relationships.collections.maps.onetomany;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

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
