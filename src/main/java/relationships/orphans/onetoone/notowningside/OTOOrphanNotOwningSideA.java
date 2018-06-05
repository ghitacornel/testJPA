package relationships.orphans.onetoone.notowningside;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OTOOrphanNotOwningSideA {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "a", orphanRemoval = true)
    private OTOOrphanNotOwningSideB b;

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

    public OTOOrphanNotOwningSideB getB() {
        return b;
    }

    public void setB(OTOOrphanNotOwningSideB b) {
        this.b = b;
    }

}
