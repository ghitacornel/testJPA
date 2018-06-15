package relationships.manytomany.cascade.oneside;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CascadeOneSideN {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "listWithNs")
    final private List<CascadeOneSideM> listWithMs = new ArrayList<>();

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

    public List<CascadeOneSideM> getListWithMs() {
        return listWithMs;
    }

}
