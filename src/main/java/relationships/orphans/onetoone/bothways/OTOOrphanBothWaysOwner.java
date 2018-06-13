package relationships.orphans.onetoone.bothways;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OTOOrphanBothWaysOwner {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(orphanRemoval = true)
    private OTOOrphanBothWaysNotOwner a;

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

    public OTOOrphanBothWaysNotOwner getA() {
        return a;
    }

    public void setA(OTOOrphanBothWaysNotOwner a) {
        this.a = a;
    }

}
