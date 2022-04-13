package relationships.unrelated;

import javax.persistence.Entity;
import javax.persistence.Id;

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
