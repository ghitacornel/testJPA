package entities.readonly;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReadOnlyEntity {

    @Id
    private Integer id;

    private String data;

    public ReadOnlyEntity() {
    }

    public ReadOnlyEntity(Integer id, String data) {
        this.id = id;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public String getData() {
        return data;
    }
}
