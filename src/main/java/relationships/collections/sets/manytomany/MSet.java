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
    private Set<NSet> mapWithNs = new HashSet<>();

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

    public Set<NSet> getMapWithNs() {
        return mapWithNs;
    }

    public void setMapWithNs(Set<NSet> mapWithNs) {
        this.mapWithNs = mapWithNs;
    }
}
