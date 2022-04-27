package relationships.collections.sets.onetomany;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ParentSet {

    @OneToMany(mappedBy = "parent")
    final private Set<ChildSet> children = new HashSet<>();
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

    public Set<ChildSet> getChildren() {
        return children;
    }
}
