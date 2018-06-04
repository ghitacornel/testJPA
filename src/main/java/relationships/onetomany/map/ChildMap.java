package relationships.onetomany.map;

import javax.persistence.*;

@Entity
public class ChildMap {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
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
