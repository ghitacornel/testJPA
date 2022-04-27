package inheritance.joined.discriminator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "IerarhieJoinedDiscriminatorB")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
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
