package frameworks.entity;

import java.io.Serializable;

/**
 * Abstract model that defines a generic toString/equals/hash code<br>
 * An entity listener over this class will affect all subclasses
 */
public abstract class AbstractEntity<T extends Serializable> {

    public abstract T getId();

    public String toString() {
        return getClass().getSimpleName() + "[id=" + getId() + "]";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        T id = getId();
        if (id == null) {
            return false;
        }
        AbstractEntity<?> o = (AbstractEntity<?>) obj;
        return id.equals(o.getId());
    }

    public int hashCode() {
        T id = getId();
        if (id == null) {
            return getClass().hashCode();
        }
        return id.hashCode();
    }
}
