package relationships.orderby.maps;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class OTOMOrderMapParent {

    @OneToMany(cascade = CascadeType.PERSIST)
    @MapKey(name = "id")
    @OrderBy("name")
    final private Map<Integer, OTOMOrderMapChild> children = new LinkedHashMap<>();
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

    public Map<Integer, OTOMOrderMapChild> getChildren() {
        return children;
    }

}
