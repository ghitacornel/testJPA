package relationships.onetoone.unidirectional.nocascade.strict;

import javax.persistence.*;

@Entity
public class Case3Child {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    private Case3Parent parent;

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

    public Case3Parent getParent() {
        return parent;
    }

    public void setParent(Case3Parent parent) {
        this.parent = parent;
    }
}
