package entities.listener.circular;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(ParentCircularListener.class)
public class ParentCircularWithListener {

    @Id
    private Integer id;

    private String name;

    private String testColumnOwn;
    private String testColumnChild1;
    private String testColumnChild2;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<ChildCircularWithListener> children = new ArrayList<>();

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

    public List<ChildCircularWithListener> getChildren() {
        return children;
    }

    public void setChildren(List<ChildCircularWithListener> children) {
        this.children = children;
    }

    public String getTestColumnOwn() {
        return testColumnOwn;
    }

    public void setTestColumnOwn(String testColumnOwn) {
        this.testColumnOwn = testColumnOwn;
    }

    public String getTestColumnChild2() {
        return testColumnChild2;
    }

    public void setTestColumnChild2(String testColumnChild2) {
        this.testColumnChild2 = testColumnChild2;
    }

    public String getTestColumnChild1() {
        return testColumnChild1;
    }

    public void setTestColumnChild1(String testColumnChild1) {
        this.testColumnChild1 = testColumnChild1;
    }
}
