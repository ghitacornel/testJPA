package inheritance;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * A mapped superclass declares mapped inherited properties<br>
 * A {@link MappedSuperclass} cannot be queried for<br>
 * A mapped superclass is not an {@link Entity}<br>
 * <p>
 * A good practice :
 * - have 1 such class as a parent for all your entities<br>
 * - this class can be typed with the type of the ID
 * - this class can provide the generic ID getter method
 * - this class can encapsulate the ID mapping and/or generation policy for all your entities<br>
 * - this class can provide default "equals" + "hashCode" methods based solely on entity ID<br>
 * - this class can provide default "toString" method based solely on entity ID and actual entity class<br>
 * - this class can be affected by entity listeners which will affect also all extensions of this class
 * => such listeners can provide data access control, add audit support, add versioning support and others
 */
@MappedSuperclass
public class InheritanceMappedSuperClass {

    @Id
    private String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }

}