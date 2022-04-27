package relationships.collections.sets.onetomany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ChildSet {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private ParentSet parent;

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

    public ParentSet getParent() {
        return parent;
    }

    public void setParent(ParentSet parent) {
        this.parent = parent;
    }

}
