package relationships.lazyloading.manytomany;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MTOMLazyN {

    @ManyToMany(mappedBy = "listWithNs")
    final private List<MTOMLazyM> listWithMs = new ArrayList<>();
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

    public List<MTOMLazyM> getListWithMs() {
        return listWithMs;
    }

}
