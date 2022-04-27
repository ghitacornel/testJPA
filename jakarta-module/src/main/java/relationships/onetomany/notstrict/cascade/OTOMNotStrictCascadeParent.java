package relationships.onetomany.notstrict.cascade;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OTOMNotStrictCascadeParent {

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
    final private List<OTOMNotStrictCascadeChild> children = new ArrayList<>();
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

    public List<OTOMNotStrictCascadeChild> getChildren() {
        return children;
    }

}
