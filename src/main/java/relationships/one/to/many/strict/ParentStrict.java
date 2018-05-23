package relationships.one.to.many.strict;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * this is a strict parent<br>
 * a strict parent is an independent {@link Entity}<br>
 * a strict parent doesn't know about his children
 *
 * @author Cornel
 */
@Entity
public class ParentStrict {

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
}
