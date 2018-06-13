package inheritance.change;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * if @DiscriminatorValue is not specified a default value as specified by specs is used
 */
@Entity
@DiscriminatorValue("A")
public class ConcreteClassA extends ConcreteSuperClass {

    // cannot be not null since it'a a one table per hierarchy strategy
    private String specificA;

    public String getSpecificA() {
        return specificA;
    }

    public void setSpecificA(String specificA) {
        this.specificA = specificA;
    }

}
