package relationships.collections.maps.onetomany;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class ParentMap {

    @OneToMany(mappedBy = "parent")
    @MapKey(name = "id")
    final private Map<Long, ChildMap> children = new HashMap<>();
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;

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

}
