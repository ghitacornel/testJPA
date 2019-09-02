package relationships.lazyloading.manytomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MTOMLazyM {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    final private List<MTOMLazyN> listWithNs = new ArrayList<>();

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

    public List<MTOMLazyN> getListWithNs() {
        return listWithNs;
    }

}
