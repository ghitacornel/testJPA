package relationships.orderby.sets;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class OTOMOrderSetParent {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST)
    @OrderBy("name")
    final private Set<OTOMOrderSetChild> children = new LinkedHashSet<>();

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

    public Set<OTOMOrderSetChild> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "[" + this.getClass() + "|" + id + "|" + name + "]";
    }

}
