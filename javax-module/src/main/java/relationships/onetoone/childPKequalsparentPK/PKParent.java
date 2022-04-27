package relationships.onetoone.childPKequalsparentPK;

import javax.persistence.*;

@Entity
public class PKParent {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "parent")
    private PKChild child;

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

    public PKChild getChild() {
        return child;
    }

    public void setChild(PKChild child) {
        this.child = child;
    }
}
