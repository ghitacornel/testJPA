package inheritance.joined.discriminator;

import jakarta.persistence.*;

@Entity
@Table(name = "IerarhieJoinedDiscriminatorA")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@DiscriminatorValue("JoinChildA")
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
