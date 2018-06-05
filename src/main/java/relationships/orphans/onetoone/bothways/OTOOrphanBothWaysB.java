package relationships.orphans.onetoone.bothways;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OTOOrphanBothWaysB {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(orphanRemoval = true)
    private OTOOrphanBothWaysA a;

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

    public OTOOrphanBothWaysA getA() {
        return a;
    }

    public void setA(OTOOrphanBothWaysA a) {
        this.a = a;
    }

}
