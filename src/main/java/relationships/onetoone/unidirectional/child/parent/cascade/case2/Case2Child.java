package relationships.onetoone.unidirectional.child.parent.cascade.case2;

import javax.persistence.*;

@Entity
public class Case2Child {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Case2Parent parent;

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

    public Case2Parent getParent() {
        return parent;
    }

    public void setParent(Case2Parent parent) {
        this.parent = parent;
    }
}
