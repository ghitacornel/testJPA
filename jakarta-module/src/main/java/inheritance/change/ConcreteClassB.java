package inheritance.change;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class ConcreteClassB extends ConcreteSuperClass {

    // cannot be not null since it'a a one table per hierarchy strategy
    private String specificB;

    public String getSpecificB() {
        return specificB;
    }

    public void setSpecificB(String specificB) {
        this.specificB = specificB;
    }

}
