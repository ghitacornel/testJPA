package entities.listener.circular;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@EntityListeners(ChildCircularListener.class)
public class ChildCircularWithListener {

    @Id
    private Integer id;

    private String name;

    private String testColumnOwn;
    private String testColumnOther;

    @ManyToOne
    private ParentCircularWithListener parent;

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

    public ParentCircularWithListener getParent() {
        return parent;
    }

    public void setParent(ParentCircularWithListener parent) {
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
