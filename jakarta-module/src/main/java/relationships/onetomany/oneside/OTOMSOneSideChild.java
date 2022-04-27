package relationships.onetomany.oneside;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * This class represents the MANY part of a ONE TO MANY relationship
 * The parents knows and control their children<br>
 * The child is the owner of the relationship<br>
 * Cascade options must NOT BE used in this case on child part since child are fully dependent on their parents<br>
 * Cascade options must BE used in this case on parent part since parents fully control their children<br>
 * It is not allowed for a child not to have a parent<br>
 * This relationship is similar to ONE TO ONE
 */
@Entity
public class OTOMSOneSideChild {

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
