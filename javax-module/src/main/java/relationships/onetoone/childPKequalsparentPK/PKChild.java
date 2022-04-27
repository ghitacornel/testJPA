package relationships.onetoone.childPKequalsparentPK;

import javax.persistence.*;

@Entity
public class PKChild {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    /**
     * observe relationship maps Child PK to Parent PK
     * it is recommended due to STRICT relationship types
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private PKParent parent;

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

    public PKParent getParent() {
        return parent;
    }

    public void setParent(PKParent parent) {
        this.parent = parent;
    }

}
