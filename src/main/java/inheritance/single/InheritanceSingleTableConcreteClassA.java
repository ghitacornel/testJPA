package inheritance.single;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class InheritanceSingleTableConcreteClassA extends InheritanceSingleTableSuperClass {

    @Basic
    // cannot be not null since it'a a one table per hierarchy strategy
    private String specificA;

    public String getSpecificA() {
        return specificA;
    }

    public void setSpecificA(String specificA) {
        this.specificA = specificA;
    }

}
