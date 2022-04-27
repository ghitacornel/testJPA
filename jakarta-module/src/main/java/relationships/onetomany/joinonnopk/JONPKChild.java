package relationships.onetomany.joinonnopk;

import jakarta.persistence.*;

@Entity
public class JONPKChild {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    /**
     * observe relationship does not use primary keys
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "parent_name", referencedColumnName = "name")
    private JONPKParent parent;

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

    public JONPKParent getParent() {
        return parent;
    }

    public void setParent(JONPKParent parent) {
        this.parent = parent;
    }

}
