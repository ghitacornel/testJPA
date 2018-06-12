package inheritance.single;

import inheritance.InheritanceMappedSuperClass;

import javax.persistence.*;

/**
 * not all inheritance types are supported<br>
 * a discriminator column is mandatory when mapping class hierarchies to a single table<br>
 * if @DiscriminatorColumn is not specified then check defaults specified by the specification<br>
 * the discriminator column values are managed only by the JPA<br>
 * the discriminator column must not be mapped to a property<br>
 * the parent class of the hierarchy can be a concrete or abstract class<br>
 *
 * @author Cornel
 */
@Entity
@Table(name = "Ierarhie")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator")
public abstract class InheritanceSingleTableSuperClass extends InheritanceMappedSuperClass {

    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
