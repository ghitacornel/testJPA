package relationships.orphans.onetomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OTOMOrphanA {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(orphanRemoval = true, mappedBy = "a")
    private List<OTOMOrphanB> bs = new ArrayList<>();

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

    public List<OTOMOrphanB> getBs() {
        return bs;
    }

    public void setBs(List<OTOMOrphanB> bs) {
        this.bs = bs;
    }
}
