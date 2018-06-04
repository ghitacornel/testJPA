package relationships.onetoone.unidirectional.child.parent.nocascade.notstrict;

import javax.persistence.*;

@Entity
public class Case4Child {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Case4Parent parent;

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

    public Case4Parent getParent() {
        return parent;
    }

    public void setParent(Case4Parent parent) {
        this.parent = parent;
    }
}
