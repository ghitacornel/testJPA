package entities.inheritance.joined.discriminator;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "IerarhieJoinedDiscriminatorA")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class InheritanceJoinedTablesDiscriminatorConcreteClassA extends InheritanceJoinedTablesDiscriminatorSuperClass {

    @Basic
    private String specificA;

    public String getSpecificA() {
        return specificA;
    }

    public void setSpecificA(String specificA) {
        this.specificA = specificA;
    }

    @Override
    public String toString() {
        return super.toString() + "[id=" + getId() + ",name=" + getName()
                + ",specificA=" + specificA + "]";
    }

}
