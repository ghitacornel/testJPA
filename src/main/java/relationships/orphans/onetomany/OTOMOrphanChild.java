package relationships.orphans.onetomany;

import javax.persistence.*;

@Entity
public class OTOMOrphanChild {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private OTOMOrphanParent parent;

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

    public OTOMOrphanParent getParent() {
        return parent;
    }

    public void setParent(OTOMOrphanParent parent) {
        this.parent = parent;
    }

}
