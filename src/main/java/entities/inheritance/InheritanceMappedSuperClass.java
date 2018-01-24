package entities.inheritance;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * this mapped superclass has no value other than specifying some mapped inherited properties<br>
 * A {@link MappedSuperclass} cannot be queried, it is not an {@link Entity}<br>
 * <p>
 * a good practice is to have such 1 class as a parent for all your entities.
 * This class will hold the id mapping and generation policy for all your
 * entities.
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
