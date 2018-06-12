package relationships.collections.maps.manytomany;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "N")
public class NMap {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "mapWithNs")
    @MapKey(name = "id")
    private Map<Integer, MMap> mapWithNs = new HashMap<>();

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

    @Override
    public String toString() {
        return "[" + this.getClass() + "|" + id + "|" + name + "]";
    }

    public Map<Integer, MMap> getMapWithNs() {
        return mapWithNs;
    }

    public void setMapWithNs(Map<Integer, MMap> mapWithNs) {
        this.mapWithNs = mapWithNs;
    }

}
