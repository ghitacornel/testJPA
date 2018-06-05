package relationships.lazyloading.onetomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OTOMLazyParent {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "parent")
    private List<OTOMLazyChild> children = new ArrayList<>();

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

    public List<OTOMLazyChild> getChildren() {
        return children;
    }

    public void setChildren(List<OTOMLazyChild> children) {
        this.children = children;
    }
}
