package entities.inheritance.single.table;

import entities.inheritance.InheritanceMappedSuperClass;

import javax.persistence.*;

/**
 * not all inheritance types are supported<br>
 * a discriminator column is mandatory when mapping class hierarchies to a
 * single table. if @DiscriminatorColumn is not specified then defaults are
 * specified by the specification<br>
 * the discriminator column values are managed only by the JPA hence no need to
 * map this column to a property<br>
 * the parent class of the hierarchy can be a concrete class
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
