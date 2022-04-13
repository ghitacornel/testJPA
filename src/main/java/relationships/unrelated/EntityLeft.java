package relationships.unrelated;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntityLeft {

    @Id
    public Integer id;

    public String correlation;

    public EntityLeft() {
    }

    public EntityLeft(Integer id, String correlation) {
        this.id = id;
        this.correlation = correlation;
    }


}
