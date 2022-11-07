package relationships.ofelements;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;

@Entity
public class ParentOfElements {

    @Id
    private Integer id;

    private String name;

    @ElementCollection
    private Set<String> elements;

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

    public Set<String> getElements() {
        return elements;
    }

    public void setElements(Set<String> elements) {
        this.elements = elements;
    }
}
