package inheritance.single;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class InheritanceSingleTableConcreteClassA extends InheritanceSingleTableSuperClass {

    // cannot be not null since it'a a one table per hierarchy strategy
    private String specificA;

    public String getSpecificA() {
        return specificA;
    }

    public void setSpecificA(String specificA) {
        this.specificA = specificA;
    }

}
