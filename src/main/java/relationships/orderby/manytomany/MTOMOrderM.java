package relationships.orderby.manytomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MTOMOrderM {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @OrderBy("name")
    final private List<MTOMOrderN> listWithNs = new ArrayList<>();

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
