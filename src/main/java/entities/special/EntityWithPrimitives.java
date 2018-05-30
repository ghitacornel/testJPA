package entities.special;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Good practice :<br>
 * Use primitives whenever a not null with default value column must be mapped<br>
 * this practice enforce a cleaner model and saves some null checks
 */
@Entity
public class EntityWithPrimitives {

    @Id
    private Integer id;

    // note that no marker is needed; JPA default value used is the same default value used by Java for this primitive
    private boolean aBoolean;

    // note that no marker is needed; here a default vale is specified other than the default value used by Java for this primitive
    private int anInt = 3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }
}