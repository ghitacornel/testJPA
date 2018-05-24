package relationships.many.to.many.list.cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CascadeN {

    @ManyToMany(mappedBy = "listWithNs", cascade = CascadeType.ALL)
    private List<CascadeM> listWithMs = new ArrayList<>();

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

    @Override
    public String toString() {
        return "[" + this.getClass() + "|" + id + "|" + name + "]";
    }

    public List<CascadeM> getListWithMs() {
        return listWithMs;
    }

}
