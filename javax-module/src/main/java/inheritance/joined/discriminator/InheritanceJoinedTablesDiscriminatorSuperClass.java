package inheritance.joined.discriminator;

import inheritance.InheritanceMappedSuperClass;

import javax.persistence.*;

/**
 * not all inheritance types are supported<br>
 * a discriminator column is not required in this case, but additional sql joins will be performed<br>
 * the parent class of the hierarchy can be a concrete or abstract class
 */
@Entity
@Table(name = "IerarhieJoinedDiscriminator")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discriminator", length = 100)// observe specified discriminator to use
public class InheritanceJoinedTablesDiscriminatorSuperClass extends InheritanceMappedSuperClass {

    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
