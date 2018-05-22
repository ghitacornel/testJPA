package entities.inheritance;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * this mapped superclass has no value other than specifying some mapped inherited properties<br>
 * A {@link MappedSuperclass} cannot be queried, it is not an {@link Entity}<br>
 * <p>
 * A good practice :
 * - have 1 such class as a parent for all your entities<br>
 * - ensure this class holds the id mapping and generation policy for all your entities<br>
 * - ensure this class provides default "equals" and "hashCode" methods based only on entity ID<br>
 * - ensure this class provides default "toString" method based only on entity ID<br>
 *
 * @author Cornel
 */
@MappedSuperclass
public class InheritanceMappedSuperClass {

    @Id
    private String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }

}
