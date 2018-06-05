package relationships.orphans.onetomany;

import javax.persistence.*;

@Entity
public class OTOMOrphanB {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private OTOMOrphanA a;

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

    public OTOMOrphanA getA() {
        return a;
    }

    public void setA(OTOMOrphanA a) {
        this.a = a;
    }

}
