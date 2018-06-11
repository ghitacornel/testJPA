package relationships.manytomany.list.cascade.bothways;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CascadeBothWaysN {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "listWithNs", cascade = CascadeType.ALL)
    private List<CascadeBothWaysM> listWithMs = new ArrayList<>();

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

    public List<CascadeBothWaysM> getListWithMs() {
        return listWithMs;
    }

}
