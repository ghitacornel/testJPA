package entities.inheritance.mixed;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * if @DiscriminatorValue is not specified a default value as specified by specs
 * is used
 *
 * @author Cornel
 */
@Entity
@DiscriminatorValue("A")
public class ConcreteClassA extends ConcreteSuperClass {

    @Basic
    // cannot be not null since it'a a one table per hierarchy strategy
    private String specificA;

    public String getSpecificA() {
        return specificA;
    }

    public void setSpecificA(String specificA) {
        this.specificA = specificA;
    }

    @Override
    public String toString() {
        return super.toString() + "[id=" + getId() + ", name=" + getName()
                + ", specificA=" + specificA + "]";
    }

}
