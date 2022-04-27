package relationships.lazyloading.onetoone;

import javax.persistence.*;

@Entity
public class OTOOB {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private OTOOA a;

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

    public OTOOA getA() {
        return a;
    }

    public void setA(OTOOA a) {
        this.a = a;
    }

}
