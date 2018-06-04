package relationships.manytoone.cascade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This class represents the ONE part of a MANY TO ONE relationship<br>
 * a strict parent is an independent entity<br>
 * a strict parent doesn't know about its children
 */
@Entity
public class MTOOCascadeParent {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

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
}
