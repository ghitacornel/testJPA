package entities.inheritance.joined.discriminator;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "IerarhieJoinedDiscriminatorB")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class InheritanceJoinedTablesDiscriminatorConcreteClassB extends InheritanceJoinedTablesDiscriminatorSuperClass {

    @Basic
    private String specificB;

    public String getSpecificB() {
        return specificB;
    }

    public void setSpecificB(String specificB) {
        this.specificB = specificB;
    }

    @Override
    public String toString() {
        return super.toString() + "[id=" + getId() + ",name=" + getName()
                + ",specificB=" + specificB + "]";
    }
}
