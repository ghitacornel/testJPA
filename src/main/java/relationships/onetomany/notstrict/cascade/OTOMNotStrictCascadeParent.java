package relationships.onetomany.notstrict.cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OTOMNotStrictCascadeParent {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
    final private List<OTOMNotStrictCascadeChild> children = new ArrayList<>();

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

    public List<OTOMNotStrictCascadeChild> getChildren() {
        return children;
    }

}
