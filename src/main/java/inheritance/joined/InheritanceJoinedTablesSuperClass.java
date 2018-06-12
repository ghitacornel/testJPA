package inheritance.joined;

import inheritance.InheritanceMappedSuperClass;

import javax.persistence.*;

/**
 * not all inheritance types are supported<br>
 * a discriminator column is not required in this case, but additional SQL joins will be performed<br>
 * the parent class of the hierarchy can be a concrete or abstract class <br>
 *
 * @author Cornel
 */
@Entity
@Table(name = "IerarhieJoined")
@Inheritance(strategy = InheritanceType.JOINED)
public class InheritanceJoinedTablesSuperClass extends InheritanceMappedSuperClass {

    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
