package relationships.one.to.many.strict;

import javax.persistence.*;

/**
 * this class serves as example of a strict 1 to many relationship<br>
 * The children knows about their parents but not vice-versa<br>
 * In this case the owner of the relationship is the child<br>
 * In this case the cascade options must not be used since parents must not be
 * affected by children related changes
 *
 * @author Cornel
 */
@Entity
public class ChildStrict {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    // this is actually the default also
    private ParentStrict parent;

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

    public ParentStrict getParent() {
        return parent;
    }

    public void setParent(ParentStrict parent) {
        this.parent = parent;
    }

}
