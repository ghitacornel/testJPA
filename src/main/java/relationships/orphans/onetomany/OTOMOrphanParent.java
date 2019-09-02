package relationships.orphans.onetomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OTOMOrphanParent {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(orphanRemoval = true, mappedBy = "parent")
    final private List<OTOMOrphanChild> children = new ArrayList<>();

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

    public List<OTOMOrphanChild> getChildren() {
        return children;
    }
}
