package entities.simple.special;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class EntityWithTransient {

    @Id
    private Integer id;

    /**
     * one case of transient
     */
    transient private Boolean transientBoolean;

    /**
     * another case of transient
     */
    @Transient
    private Integer transientInteger;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getTransientBoolean() {
        return transientBoolean;
    }

    public void setTransientBoolean(Boolean transientBoolean) {
        this.transientBoolean = transientBoolean;
    }

    public Integer getTransientInteger() {
        return transientInteger;
    }

    public void setTransientInteger(Integer transientInteger) {
        this.transientInteger = transientInteger;
    }
}
