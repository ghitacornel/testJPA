package inheritance.single;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class InheritanceSingleTableConcreteClassB extends InheritanceSingleTableSuperClass {

    // cannot be not null since it'a a one table per hierarchy strategy
    private String specificB;

    public String getSpecificB() {
        return specificB;
    }

    public void setSpecificB(String specificB) {
        this.specificB = specificB;
    }

}
