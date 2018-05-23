package relationships.many.to.many.list.cascade;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CascadeM {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "CascadeMN",
            joinColumns = {@JoinColumn(name = "id_m", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_n", referencedColumnName = "id")}
    )
    private List<CascadeN> listWithNs = new ArrayList<>();

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

    public List<CascadeN> getListWithNs() {
        return listWithNs;
    }

    @Override
    public String toString() {
        return "[" + this.getClass() + "|" + id + "|" + name + "]";
    }

}
