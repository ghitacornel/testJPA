package inheritance.single;

import inheritance.InheritanceMappedSuperClass;

import javax.persistence.*;

/**
 * not all inheritance types are supported<br>
 * a discriminator database column is mandatory when mapping class hierarchies to a single table<br>
 * if @DiscriminatorColumn is not specified then JPA specifies and use defaults<br>
 * the discriminator database column values are managed only by the JPA<br>
 * the discriminator database column must not be mapped to an entity property<br>
 * the parent class of the hierarchy can be a concrete or abstract class
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
