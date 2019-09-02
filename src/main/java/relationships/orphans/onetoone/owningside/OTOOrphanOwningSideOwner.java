package relationships.orphans.onetoone.owningside;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OTOOrphanOwningSideOwner {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(orphanRemoval = true)
    private OTOOrphanOwningSideNotOwner a;

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

    public OTOOrphanOwningSideNotOwner getA() {
        return a;
    }

    public void setA(OTOOrphanOwningSideNotOwner a) {
        this.a = a;
    }

}
