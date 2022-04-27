package relationships.unrelated;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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
