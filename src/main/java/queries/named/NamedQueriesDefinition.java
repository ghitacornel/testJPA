package queries.named;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * a {@link NamedQuery} can be placed anywhere<br>
 * a {@link NamedQuery} can be placed inside a {@link NamedQueries}<br>
 * <p>
 * in production is recommended to have {@link NamedQueries} defined in
 * {@link Entity} classes they operate on<br>
 * in production is recommended to prefix names of {@link NamedQuery} with the
 * simple class entity name they operate on<br>
 * <p>
 * we used this mapped abstract class to have all named queries in one place
 * only for demo purposes<br>
 *
 * @author Cornel
 */
@MappedSuperclass
@NamedQueries({
        @NamedQuery(name = "Entity.findByName", query = "select t from Entity t where lower(t.name) like :name"),
        @NamedQuery(name = "Entity.findById", query = "select t from Entity t where t.id = ?1")})
@NamedQuery(name = "Entity.findByExactName", query = "select t from Entity t where t.name = :name")
public class NamedQueriesDefinition {
}
