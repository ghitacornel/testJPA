package entities.simple;

import javax.persistence.*;
import java.util.Date;

/**
 * a simple {@link javax.persistence.Entity}<br>
 * jpa default mappings usage is encouraged<br>
 * jpa enum mapping usage is encourage over standard database dictionaries
 * whenever possible<br>
 *
 * @author Cornel
 */
@javax.persistence.Entity
@Table(name = "SimpleEntity")
public class Entity {

    @Id
    private Integer id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic
    private Integer value;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "simpleDate")
    private Date simpleDate;

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

    public Date getSimpleDate() {
        return simpleDate;
    }

    public void setSimpleDate(Date simpleDate) {
        this.simpleDate = simpleDate;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
