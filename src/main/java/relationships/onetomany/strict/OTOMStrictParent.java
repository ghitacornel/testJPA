package relationships.onetomany.strict;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the ONE part of a ONE TO MANY relationship<br>
 * a strict parent is an independent entity<br>
 * a strict parent knows and control its children
 */
@Entity
public class OTOMStrictParent {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parent")
    final private List<OTOMStrictChild> children = new ArrayList<>();

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

    public List<OTOMStrictChild> getChildren() {
        return children;
    }

}
