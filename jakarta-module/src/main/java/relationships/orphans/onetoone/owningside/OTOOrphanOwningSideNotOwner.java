package relationships.orphans.onetoone.owningside;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class OTOOrphanOwningSideNotOwner {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "a")
    private OTOOrphanOwningSideOwner b;

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

    public OTOOrphanOwningSideOwner getB() {
        return b;
    }

    public void setB(OTOOrphanOwningSideOwner b) {
        this.b = b;
    }

}
