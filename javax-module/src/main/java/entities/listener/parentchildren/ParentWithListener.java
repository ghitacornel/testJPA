package entities.listener.parentchildren;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(ParentListener.class)
public class ParentWithListener {

    @Id
    private Integer id;

    private String name;

    private String testColumnOwn;
    private String testColumnOther;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<ChildWithListener> children = new ArrayList<>();

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

    public List<ChildWithListener> getChildren() {
        return children;
    }

    public void setChildren(List<ChildWithListener> children) {
        this.children = children;
    }

    public String getTestColumnOwn() {
        return testColumnOwn;
    }

    public void setTestColumnOwn(String testColumnOwn) {
        this.testColumnOwn = testColumnOwn;
    }

    public String getTestColumnOther() {
        return testColumnOther;
    }

    public void setTestColumnOther(String testColumnOther) {
        this.testColumnOther = testColumnOther;
    }
}
