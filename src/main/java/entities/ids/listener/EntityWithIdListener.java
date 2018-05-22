package entities.ids.listener;

import javax.persistence.*;
import java.util.UUID;

/**
 * Note that an id must be immutable since it can be used as a key (in a map)<br>
 * <br>
 * A simple {@link Id} (just a flag) is enough<br>
 * <br>
 * In case no id generation strategy is specified you'll have to provide
 * manually the id and {@link UUID} is perfect for this purpose. Note that
 * {@link UUID} is suitable also for data migration across different databases.
 * A listener for automatically setting the id works perfectly. <br>
 * <br>
 * Use convention if id is null then entity is not persisted.<br>
 * <br>
 * {@link EmbeddedId}, {@link IdClass} are reserved for id's on more than 1
 * property (database column). This is more suitable for old/theoretical
 * correctness, better is to use a combination of generated id and uniqueness on
 * multiple columns<br>
 * <br>
 * Note that a simple {@link GenerationType#SEQUENCE} is enough to specify a
 * sequence (Oracle DB)
 *
 * @author Cornel
 */

@Entity
@EntityListeners(EntityIdGeneratorListener.class)
public class EntityWithIdListener {

    // TODO must have ID visibility set at this level in order to be accessible from listener
    @Id
    String id;

    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
