package relationships.onetoone.unidirectional.child.parent.cascade.case1;

import javax.persistence.*;

@Entity
public class Case1Child {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private Case1Parent parent;

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

    public Case1Parent getParent() {
        return parent;
    }

    public void setParent(Case1Parent parent) {
        this.parent = parent;
    }
}
