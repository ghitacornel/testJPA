package inheritance.joined.discriminator;

import jakarta.persistence.*;

@Entity
@Table(name = "IerarhieJoinedDiscriminatorB")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@DiscriminatorValue("JoinChildB")
public class InheritanceJoinedTablesDiscriminatorConcreteClassB extends InheritanceJoinedTablesDiscriminatorSuperClass {

    @Column(nullable = false)
    private String specificB;

    public String getSpecificB() {
        return specificB;
    }

    public void setSpecificB(String specificB) {
        this.specificB = specificB;
    }

}
