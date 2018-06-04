package relationships.onetomany.strict;

import javax.persistence.*;

@Entity
public class OTOMStrictChild {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private OTOMStrictParent parent;

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

    public OTOMStrictParent getParent() {
        return parent;
    }

    public void setParent(OTOMStrictParent parent) {
        this.parent = parent;
    }

}
