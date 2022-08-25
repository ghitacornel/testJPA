package relationships.manytomany.nocascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NoCascadeM {

    /**
     * observe override the join table definition<br>
     * observe unique constraint added on foreign keys combination<br>
     * observe not null constraints added
     * observe no cascade
     * this entity is the JPA owner of the relationship
     */
    @ManyToMany
    @JoinTable(
            name = "NoCascadeMN",
            joinColumns = {@JoinColumn(name = "id_m", referencedColumnName = "NoCascadeM_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "id_n", referencedColumnName = "NoCascadeN_ID", nullable = false)},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"id_m", "id_n"})}
    )
    final private List<NoCascadeN> listWithNs = new ArrayList<>();
    @Id
    @Column(name = "NoCascadeM_ID")
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

    public List<NoCascadeN> getListWithNs() {
        return listWithNs;
    }

}
