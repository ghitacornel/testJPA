package relationships.orphans.onetoone.bothways;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OTOOrphanBothWaysA {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(orphanRemoval = true, mappedBy = "a")
    private OTOOrphanBothWaysB b;

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

    public OTOOrphanBothWaysB getB() {
        return b;
    }

    public void setB(OTOOrphanBothWaysB b) {
        this.b = b;
    }

}
