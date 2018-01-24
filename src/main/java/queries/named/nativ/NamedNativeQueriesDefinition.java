package queries.named.nativ;

import entities.simple.Entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 * a {@link NamedNativeQuery} can be placed anywhere<br>
 * a {@link NamedNativeQuery} can be placed inside a {@link NamedNativeQueries}<br>
 * <p>
 * in production is recommended to have {@link NamedNativeQuery} defined in
 * {@link javax.persistence.Entity} classes they operate on<br>
 * in production is recommended to prefix names of {@link NamedNativeQuery} with
 * the simple class entity name they operate on<br>
 * <p>
 * we used this mapped abstract class to have all named queries in one place
 * only for demo purposes<br>
 *
 * @author Cornel
 */
@MappedSuperclass
@NamedNativeQueries({
        @NamedNativeQuery(name = "Entity.findByNameNative", query = "select * from SimpleEntity where lower(name) like lower(:name)", resultClass = Entity.class),
        @NamedNativeQuery(name = "Entity.findByIdNative", query = "select * from SimpleEntity where id = ?1", resultClass = Entity.class)})
@NamedNativeQuery(name = "Entity.findByExactNameNative", query = "select * from SimpleEntity where name = ?1", resultClass = Entity.class)
public class NamedNativeQueriesDefinition {
}
