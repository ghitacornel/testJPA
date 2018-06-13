package relationships.collections.maps.manytomany;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class MMap {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "MN_MAP", joinColumns = {@JoinColumn(name = "id_m", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "id_n", referencedColumnName = "id")})
    @MapKey(name = "id")
    private Map<Integer, NMap> mapWithNs = new HashMap<>();

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

    public Map<Integer, NMap> getMapWithNs() {
        return mapWithNs;
    }

    public void setMapWithNs(Map<Integer, NMap> mapWithNs) {
        this.mapWithNs = mapWithNs;
    }

}
