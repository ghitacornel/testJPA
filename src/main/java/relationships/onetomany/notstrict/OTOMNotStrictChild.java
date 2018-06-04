package relationships.onetomany.notstrict;

import javax.persistence.*;

/**
 * This class represents the MANY part of a ONE TO MANY relationship
 * The children know about their parents<br>
 * The parents know but do not control their children<br>
 * The child is the owner of the relationship<br>
 * Cascade options must NOT BE used in this case on child part since child can be independent of their parents<br>
 * Cascade options must NOT BE used in this case on parent part since parents do not fully control their children<br>
 * It is allowed for a child not to have a parent<br>
 * This relationship is similar to ONE TO ONE
 */
@Entity
public class OTOMNotStrictChild {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private OTOMNotStrictParent parent;

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

    public OTOMNotStrictParent getParent() {
        return parent;
    }

    public void setParent(OTOMNotStrictParent parent) {
        this.parent = parent;
    }

}
