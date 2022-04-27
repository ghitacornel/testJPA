package relationships.orphans.onetoone.notowningside;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class OTOOrphanNotOwningSideOwner {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private OTOOrphanNotOwningSideNotOwner a;

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

    public OTOOrphanNotOwningSideNotOwner getA() {
        return a;
    }

    public void setA(OTOOrphanNotOwningSideNotOwner a) {
        this.a = a;
    }

}
