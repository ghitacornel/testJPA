package relationships.manytomany.nocascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NoCascadeM {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "NoCascadeMN",
            joinColumns = {@JoinColumn(name = "id_m", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_n", referencedColumnName = "id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"id_m", "id_n"})}
    )
    private List<NoCascadeN> listWithNs = new ArrayList<>();

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

    public List<NoCascadeN> getListWithNs() {
        return listWithNs;
    }

    @Override
    public String toString() {
        return "[" + this.getClass() + "|" + id + "|" + name + "]";
    }

}
