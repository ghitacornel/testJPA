package relationships.lazyloading.onetomany;

import jakarta.persistence.*;

@Entity
public class OTOMLazyChild {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private OTOMLazyParent parent;

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

    public OTOMLazyParent getParent() {
        return parent;
    }

    public void setParent(OTOMLazyParent parent) {
        this.parent = parent;
    }

}
