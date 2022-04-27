package relationships.unrelated;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EntityRight {

    @Id
    public Integer id;

    public String correlation;

    public EntityRight() {
    }

    public EntityRight(Integer id, String correlation) {
        this.id = id;
        this.correlation = correlation;
    }

}
