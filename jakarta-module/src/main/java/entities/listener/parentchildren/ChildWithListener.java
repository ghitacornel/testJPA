package entities.listener.parentchildren;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@EntityListeners(ChildListener.class)
public class ChildWithListener {

    @Id
    private Integer id;

    private String name;

    private String testColumnOwn;
    private String testColumnOther;

    @ManyToOne
    private ParentWithListener parent;

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

    public ParentWithListener getParent() {
        return parent;
    }

    public void setParent(ParentWithListener parent) {
        this.parent = parent;
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
