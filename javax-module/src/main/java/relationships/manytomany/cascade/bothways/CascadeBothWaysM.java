package relationships.manytomany.cascade.bothways;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CascadeBothWaysM {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "CascadeBothWaysMN",
            joinColumns = {@JoinColumn(name = "id_m", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_n", referencedColumnName = "id")}
    )
    final private List<CascadeBothWaysN> listWithNs = new ArrayList<>();
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

    public List<CascadeBothWaysN> getListWithNs() {
        return listWithNs;
    }

}
