package queries.named.nativ;

import entities.relationships.one.to.many.bidirectional.list.Child;
import entities.relationships.one.to.many.bidirectional.list.Parent;

import javax.persistence.*;

/**
 * example with a {@link NamedNativeQuery} mapped to domain using a * {@link SqlResultSetMapping}
 *
 * @author Cornel
 */
@MappedSuperclass
@SqlResultSetMapping(name = "customParentChildMapping", entities = {
        @EntityResult(entityClass = Parent.class, fields = {
                @FieldResult(name = "id", column = "p_id"),
                @FieldResult(name = "name", column = "p_name")}),
        @EntityResult(entityClass = Child.class, fields = {
                @FieldResult(name = "id", column = "c_id"),
                @FieldResult(name = "name", column = "c_name"),
                @FieldResult(name = "parent", column = "p_id")})}, columns = {
        @ColumnResult(name = "p_id"), @ColumnResult(name = "p_name"),
        @ColumnResult(name = "c_id"), @ColumnResult(name = "c_name")})
@NamedNativeQuery(name = "Parent.findWithChildrenNative", query = "select p.id as p_id, p.name as p_name, c.id as c_id, c.name as c_name from Parent p, Child c where p.id = c.parent_id", resultSetMapping = "customParentChildMapping")
public class NamedNativeQueriesWithResultSetMappingDefinition {

    // XXX note that child.parent was mapped to a column id using
    // @FieldResult(name = "parent", column = "p_id")

    // XXX very weird Hibernate mapping case but testable anyway
    // looks like Hibernate requires mapping of ids for dependable entities

    // this kind of mapping is useful for retrieving in 1 native query all
    // needed results

}