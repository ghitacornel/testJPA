package relationships.manytomany.cascade.bothways;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CascadeBothWaysN {

    @ManyToMany(mappedBy = "listWithNs", cascade = CascadeType.ALL)
    final private List<CascadeBothWaysM> listWithMs = new ArrayList<>();
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

    public List<CascadeBothWaysM> getListWithMs() {
        return listWithMs;
    }

}
