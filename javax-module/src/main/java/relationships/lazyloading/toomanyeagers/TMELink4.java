package relationships.lazyloading.toomanyeagers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TMELink4 {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private TMECenter center;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[" + this.getClass() + "|" + id + "|" + name + "]";
    }

    public TMECenter getCenter() {
        return center;
    }

    public void setCenter(TMECenter center) {
        this.center = center;
    }
}
