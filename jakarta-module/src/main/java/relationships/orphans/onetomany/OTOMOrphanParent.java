package relationships.orphans.onetomany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OTOMOrphanParent {

    @OneToMany(orphanRemoval = true, mappedBy = "parent")
    final private List<OTOMOrphanChild> children = new ArrayList<>();
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

    public List<OTOMOrphanChild> getChildren() {
        return children;
    }
}
