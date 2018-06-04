package relationships.manytoone.cascade;

import javax.persistence.*;

/**
 * Cascading with this kind of relationship is bad
 */
@Entity
public class MTOOCascadeChild {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.ALL})
    private MTOOCascadeParent parent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MTOOCascadeParent getParent() {
        return parent;
    }

    public void setParent(MTOOCascadeParent parent) {
        this.parent = parent;
    }

}
