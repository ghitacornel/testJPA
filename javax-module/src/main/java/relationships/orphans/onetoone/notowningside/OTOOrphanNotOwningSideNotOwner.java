package relationships.orphans.onetoone.notowningside;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OTOOrphanNotOwningSideNotOwner {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "a", orphanRemoval = true)
    private OTOOrphanNotOwningSideOwner b;

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

    public OTOOrphanNotOwningSideOwner getB() {
        return b;
    }

    public void setB(OTOOrphanNotOwningSideOwner b) {
        this.b = b;
    }

}
