package relationships.lazyloading.toomanyeagers;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TMECenter {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private TMELink1 link1;

    @ManyToOne(fetch = FetchType.EAGER)
    private TMELink2 link2;

    @OneToMany(fetch = FetchType.EAGER)
    private List<TMELink3> links3 = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<TMELink4> links4 = new ArrayList<>();

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

    public TMELink1 getLink1() {
        return link1;
    }

    public void setLink1(TMELink1 link1) {
        this.link1 = link1;
    }

    public TMELink2 getLink2() {
        return link2;
    }

    public void setLink2(TMELink2 link2) {
        this.link2 = link2;
    }

    public List<TMELink3> getLinks3() {
        return links3;
    }

    public void setLinks3(List<TMELink3> links3) {
        this.links3 = links3;
    }

    public List<TMELink4> getLinks4() {
        return links4;
    }

    public void setLinks4(List<TMELink4> links4) {
        this.links4 = links4;
    }
}
