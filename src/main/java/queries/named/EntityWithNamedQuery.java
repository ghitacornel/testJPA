package queries.named;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = "EntityWithNamedQuery.findByName", query = "select t from EntityWithNamedQuery t where lower(t.name) like :name")
@NamedQuery(name = "EntityWithNamedQuery.findById", query = "select t from EntityWithNamedQuery t where t.id = ?1")
@NamedQuery(name = "EntityWithNamedQuery.findByExactName", query = "select t from EntityWithNamedQuery t where t.name = :name")
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
