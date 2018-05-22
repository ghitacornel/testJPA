package entities.inheritance.joined.discriminator;

import javax.persistence.*;

@Entity
@Table(name = "IerarhieJoinedDiscriminatorA")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class InheritanceJoinedTablesDiscriminatorConcreteClassA extends InheritanceJoinedTablesDiscriminatorSuperClass {

    @Column(nullable = false)
    private String specificA;

    public String getSpecificA() {
        return specificA;
    }

    public void setSpecificA(String specificA) {
        this.specificA = specificA;
    }

}
