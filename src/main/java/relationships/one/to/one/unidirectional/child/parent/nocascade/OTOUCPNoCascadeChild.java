package relationships.one.to.one.unidirectional.child.parent.nocascade;

import javax.persistence.*;

@Entity
public class OTOUCPNoCascadeChild {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    private OTOUCPNoCascadeParent parent;

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

    public OTOUCPNoCascadeParent getParent() {
        return parent;
    }

    public void setParent(OTOUCPNoCascadeParent parent) {
        this.parent = parent;
    }
}
