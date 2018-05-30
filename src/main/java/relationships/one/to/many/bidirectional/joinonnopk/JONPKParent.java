package relationships.one.to.many.bidirectional.joinonnopk;

import javax.persistence.*;
import java.util.List;

@Entity
public class JONPKParent {

    @Id
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parent")
    private List<JONPKChild> children;

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

    public List<JONPKChild> getChildren() {
        return children;
    }

    public void setChildren(List<JONPKChild> children) {
        this.children = children;
    }
}