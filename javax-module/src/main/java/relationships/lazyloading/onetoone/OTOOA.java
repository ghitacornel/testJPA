package relationships.lazyloading.onetoone;

import javax.persistence.*;

@Entity
public class OTOOA {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "a")
    private OTOOB b;

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

    public OTOOB getB() {
        return b;
    }

    public void setB(OTOOB b) {
        this.b = b;
    }

}
