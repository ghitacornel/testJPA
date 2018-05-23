package relationships.many.to.many.list;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class M {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "MN",
            joinColumns = {@JoinColumn(name = "id_m", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_n", referencedColumnName = "id")}
    )
    private List<N> listWithNs = new ArrayList<>();

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

    public List<N> getListWithNs() {
        return listWithNs;
    }

    @Override
    public String toString() {
        return "[" + this.getClass() + "|" + id + "|" + name + "]";
    }

}
