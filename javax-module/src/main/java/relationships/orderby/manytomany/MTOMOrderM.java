package relationships.orderby.manytomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MTOMOrderM {

    @ManyToMany(cascade = CascadeType.PERSIST)
    @OrderBy("name")
    final private List<MTOMOrderN> listWithNs = new ArrayList<>();
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

    public List<MTOMOrderN> getListWithNs() {
        return listWithNs;
    }

}
