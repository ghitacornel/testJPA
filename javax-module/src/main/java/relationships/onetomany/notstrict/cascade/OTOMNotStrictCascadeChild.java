package relationships.onetomany.notstrict.cascade;

import javax.persistence.*;

@Entity
public class OTOMNotStrictCascadeChild {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private OTOMNotStrictCascadeParent parent;

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

    public OTOMNotStrictCascadeParent getParent() {
        return parent;
    }

    public void setParent(OTOMNotStrictCascadeParent parent) {
        this.parent = parent;
    }

}
