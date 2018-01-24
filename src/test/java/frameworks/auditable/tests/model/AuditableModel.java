package frameworks.auditable.tests.model;

import frameworks.auditable.model.Auditable;

import javax.persistence.*;

/**
 * Auditable classes must extend a certain mapped super class
 * <p>
 * Created by Cornel on 10.07.2015.
 */
@Entity
@Table(name = "AuditableEntity")
@SequenceGenerator(name = "ids", sequenceName = "ids")
public class AuditableModel extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ids")
    private Long id;

    @Basic(optional = false)
    @Column(unique = true, name = "name", nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
