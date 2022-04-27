package relationships.manytomany.cascade.oneside;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CascadeOneSideM {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "CascadeOneSideMN",
            joinColumns = {@JoinColumn(name = "id_m", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_n", referencedColumnName = "id")}
    )
    final private List<CascadeOneSideN> listWithNs = new ArrayList<>();
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

    public List<CascadeOneSideN> getListWithNs() {
        return listWithNs;
    }

}
