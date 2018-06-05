package relationships.manytomany.bothowners;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class N {

    @ManyToMany
    @JoinTable(
            name = "MN_OK",
            joinColumns = {@JoinColumn(name = "id_n", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_m", referencedColumnName = "id")},
            uniqueConstraints = {@UniqueConstraint(name = "UK_MN_OK", columnNames = {"id_m", "id_n"})}
    )
    private List<M> listWithMs = new ArrayList<>();

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

    public List<M> getListWithMs() {
        return listWithMs;
    }

}
