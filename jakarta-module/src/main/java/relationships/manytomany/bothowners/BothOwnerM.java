package relationships.manytomany.bothowners;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BothOwnerM {

    @ManyToMany
    @JoinTable(
            name = "MN_OK",
            joinColumns = {@JoinColumn(name = "id_m", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_n", referencedColumnName = "id")},
            uniqueConstraints = {@UniqueConstraint(name = "UK_MN_OK", columnNames = {"id_m", "id_n"})}
    )
    // map it with final
    private List<BothOwnerN> listWithNs = new ArrayList<>();
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

    public List<BothOwnerN> getListWithNs() {
        return listWithNs;
    }

    // do not use this setter, it overrides the proxy
    public void setListWithNs(List<BothOwnerN> listWithNs) {
        this.listWithNs = listWithNs;
    }


}
