package relationships.collections.sets.onetomany;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ParentSet {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "parent")
    final private Set<ChildSet> children = new HashSet<>();

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

    public Set<ChildSet> getChildren() {
        return children;
    }
}
