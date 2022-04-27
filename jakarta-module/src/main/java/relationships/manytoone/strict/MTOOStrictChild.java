package relationships.manytoone.strict;

import jakarta.persistence.*;

/**
 * This class represents the MANY part of a MANY TO ONE relationship
 */
@Entity
public class MTOOStrictChild {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private MTOOStrictParent parent;

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

    public MTOOStrictParent getParent() {
        return parent;
    }

    public void setParent(MTOOStrictParent parent) {
        this.parent = parent;
    }

}
