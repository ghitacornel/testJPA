package entities.ids.listener;

import jakarta.persistence.*;
import java.util.UUID;

/**
 * An ID must be immutable since it can be used as a key (in a map)<br>
 * <br>
 * A simple {@link Id} annotation is enough<br>
 * <br>
 * In case no id generation strategy is specified one has to manually provide the id.
 * {@link UUID} is perfect for this purpose.
 * {@link UUID} is suitable also for data migration across different databases.
 * A listener for automatically setting the id is recommended in this case.
 * <br>
 * ID convention : if ID is null then entity is not persisted<br>
 * <br>
 * {@link EmbeddedId}, {@link IdClass} are reserved for id's on more than 1 property (database column).
 * This is more suitable old/theoretical correctness.
 * Instead of using composed IDs better is to use a combination of generated ID and uniqueness on multiple columns
 * <br>
 */

@Entity
@EntityListeners(EntityIdGeneratorListener.class)
public class EntityWithIdListener {

    // must have ID visibility set at this level in order to be accessible from listener
    // no need to use a setter in this case
    @Id
    String id;

    public String getId() {
        return id;
    }

}
