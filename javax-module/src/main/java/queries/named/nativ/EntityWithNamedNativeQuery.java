package queries.named.nativ;

import relationships.onetomany.strict.OTOMStrictChild;
import relationships.onetomany.strict.OTOMStrictParent;

import javax.persistence.*;


@NamedQuery(name = "EntityWithNamedNativeQuery.countAll", query = "select count(*) from EntityWithNamedNativeQuery t")
@NamedNativeQuery(name = "EntityWithNamedNativeQuery.findByNameNative", query = "select * from EntityWithNamedNativeQuery where lower(name) like lower(:name)", resultClass = EntityWithNamedNativeQuery.class)
@NamedNativeQuery(name = "EntityWithNamedNativeQuery.findByIdNative", query = "select * from EntityWithNamedNativeQuery where id = ?1", resultClass = EntityWithNamedNativeQuery.class)
@NamedNativeQuery(name = "EntityWithNamedNativeQuery.findByExactNameNative", query = "select * from EntityWithNamedNativeQuery where name = ?1", resultClass = EntityWithNamedNativeQuery.class)
@SqlResultSetMapping(name = "customParentChildMapping", entities = {
        @EntityResult(entityClass = OTOMStrictParent.class, fields = {
                @FieldResult(name = "id", column = "p_id"),
                @FieldResult(name = "name", column = "p_name")}),
        @EntityResult(entityClass = OTOMStrictChild.class, fields = {
                @FieldResult(name = "id", column = "c_id"),
                @FieldResult(name = "name", column = "c_name"),
                @FieldResult(name = "parent", column = "p_id")})}, columns = {
        @ColumnResult(name = "p_id"), @ColumnResult(name = "p_name"),
        @ColumnResult(name = "c_id"), @ColumnResult(name = "c_name")})
@NamedNativeQuery(name = "OTOMStrictParent.findWithChildrenNative", query = "select p.id as p_id, p.name as p_name, c.id as c_id, c.name as c_name from OTOMStrictParent p, OTOMStrictChild c where p.id = c.parent_id", resultSetMapping = "customParentChildMapping")
@Entity
public class EntityWithNamedNativeQuery {

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
