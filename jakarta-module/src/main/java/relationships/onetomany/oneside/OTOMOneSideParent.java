package relationships.onetomany.oneside;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the ONE part of a ONE TO MANY relationship<br>
 * a strict parent is an independent entity<br>
 * a strict parent knows and control its children
 */
@Entity
public class OTOMOneSideParent {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    // mandatory cascade all
    // mandatory orphanRemoval true
    // mandatory @JoinColumn with not null
    // mandatory final
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = false)
    private List<OTOMSOneSideChild> children = new ArrayList<>();

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

    public List<OTOMSOneSideChild> getChildren() {
        return children;
    }

    // do not use this
    public void setChildren(List<OTOMSOneSideChild> children) {
        this.children = children;
    }

}
