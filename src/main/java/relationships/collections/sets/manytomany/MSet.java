package relationships.collections.sets.manytomany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MSet {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    final private Set<NSet> setWithNs = new HashSet<>();

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

    public Set<NSet> getSetWithNs() {
        return setWithNs;
    }

}
