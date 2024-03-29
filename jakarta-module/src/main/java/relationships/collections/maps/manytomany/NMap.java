package relationships.collections.maps.manytomany;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class NMap {

    @ManyToMany(mappedBy = "mapWithNs")
    @MapKey(name = "id")
    final private Map<Integer, MMap> mapWithMs = new HashMap<>();
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

    @Override
    public String toString() {
        return "[" + this.getClass() + "|" + id + "|" + name + "]";
    }

    public Map<Integer, MMap> getMapWithMs() {
        return mapWithMs;
    }

}
