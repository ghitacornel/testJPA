package relationships.onetomany.strict;

import javax.persistence.*;

/**
 * This class represents the MANY part of a ONE TO MANY relationship
 * The children knows about their parents<br>
 * The parents knows and control their children<br>
 * The child is the owner of the relationship<br>
 * Cascade options must NOT BE used in this case on child part since child are fully dependent on their parents<br>
 * Cascade options must BE used in this case on parent part since parents fully control their children<br>
 * It is not allowed for a child not to have a parent<br>
 * This relationship is similar to ONE TO ONE
 */
@Entity
public class OTOMStrictChild {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private OTOMStrictParent parent;

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

    public OTOMStrictParent getParent() {
        return parent;
    }

    public void setParent(OTOMStrictParent parent) {
        this.parent = parent;
    }

}
