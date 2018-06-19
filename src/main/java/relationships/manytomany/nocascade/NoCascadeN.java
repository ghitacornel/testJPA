package relationships.manytomany.nocascade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NoCascadeN {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    /**
     * observe no cascade
     * observe no definition of join table is required
     * observe marking of not owning side
     */
    @ManyToMany(mappedBy = "listWithNs")
    final private List<NoCascadeM> listWithMs = new ArrayList<>();

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

    public List<NoCascadeM> getListWithMs() {
        return listWithMs;
    }

}
