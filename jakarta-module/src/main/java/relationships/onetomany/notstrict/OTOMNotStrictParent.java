package relationships.onetomany.notstrict;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the ONE part of a ONE TO MANY relationship<br>
 * a strict parent is an independent entity<br>
 * a strict parent knows but not control its children
 */
@Entity
@NamedQuery(name = "OTOMNotStrictParent.findWithChildren", query = "select p from OTOMNotStrictParent p join fetch p.children where p.id = ?1")
public class OTOMNotStrictParent {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "parent")
    // map this as final
    private List<OTOMNotStrictChild> children = new ArrayList<>();

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

    public List<OTOMNotStrictChild> getChildren() {
        return children;
    }

    // do not use setter
    public void setChildren(List<OTOMNotStrictChild> children) {
        this.children = children;
    }

}
