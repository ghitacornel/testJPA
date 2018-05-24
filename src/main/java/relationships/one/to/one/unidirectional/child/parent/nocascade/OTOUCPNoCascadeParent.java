package relationships.one.to.one.unidirectional.child.parent.nocascade;

import javax.persistence.*;

@Entity
public class OTOUCPNoCascadeParent {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

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

}
