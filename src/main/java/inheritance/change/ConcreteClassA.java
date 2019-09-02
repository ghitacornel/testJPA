package inheritance.change;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")// if @DiscriminatorValue is not specified JPA will use defaults as specified in specs
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
