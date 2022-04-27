package relationships.orphans.onetoone.bothways;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OTOOrphanBothWaysNotOwner {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(orphanRemoval = true, mappedBy = "a")
    private OTOOrphanBothWaysOwner b;

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

    public OTOOrphanBothWaysOwner getB() {
        return b;
    }

    public void setB(OTOOrphanBothWaysOwner b) {
        this.b = b;
    }

}
