package queries.named;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = "EntityWithNamedQuery.countAll", query = "select count(t) from EntityWithNamedQuery t")
@Entity
public class EntityWithNamedQuery {

    @Id
    private Integer id;

    @Basic(optional = false)
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
}
