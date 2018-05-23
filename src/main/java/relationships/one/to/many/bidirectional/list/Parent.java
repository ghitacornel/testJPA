package relationships.one.to.many.bidirectional.list;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQuery(name = "Parent.findWithChildren", query = "select p from Parent p join fetch p.children where p.id = ?1")
public class Parent {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parent")
    private List<Child> children;

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

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}
