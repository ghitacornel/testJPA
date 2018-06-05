package relationships.orphans.onetoone.owningside;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OTOOrphanOwningSideA {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "a")
    private OTOOrphanOwningSideB b;

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

    public OTOOrphanOwningSideB getB() {
        return b;
    }

    public void setB(OTOOrphanOwningSideB b) {
        this.b = b;
    }

}
