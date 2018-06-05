package relationships.onetomany.map;

import javax.persistence.*;
import java.util.Map;

@Entity
public class ParentMap {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "parent")
    @MapKey(name = "id")
    private Map<Long, ChildMap> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, ChildMap> getChildren() {
        return children;
    }

    public void setChildren(Map<Long, ChildMap> children) {
        this.children = children;
    }

}
