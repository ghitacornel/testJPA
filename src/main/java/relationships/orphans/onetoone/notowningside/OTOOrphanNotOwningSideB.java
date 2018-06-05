package relationships.orphans.onetoone.notowningside;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OTOOrphanNotOwningSideB {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private OTOOrphanNotOwningSideA a;

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

    public OTOOrphanNotOwningSideA getA() {
        return a;
    }

    public void setA(OTOOrphanNotOwningSideA a) {
        this.a = a;
    }

}
