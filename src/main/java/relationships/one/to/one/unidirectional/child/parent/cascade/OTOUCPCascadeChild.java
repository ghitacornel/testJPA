package relationships.one.to.one.unidirectional.child.parent.cascade;

import javax.persistence.*;

@Entity
public class OTOUCPCascadeChild {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private OTOUCPCascadeParent parent;

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

    public OTOUCPCascadeParent getParent() {
        return parent;
    }

    public void setParent(OTOUCPCascadeParent parent) {
        this.parent = parent;
    }
}
