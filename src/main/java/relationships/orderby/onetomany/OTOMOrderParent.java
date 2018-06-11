package relationships.orderby.onetomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OTOMOrderParent {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST)
    @OrderBy("name")
    private List<OTOMOrderChild> children = new ArrayList<>();

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

    public List<OTOMOrderChild> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "[" + this.getClass() + "|" + id + "|" + name + "]";
    }

}
