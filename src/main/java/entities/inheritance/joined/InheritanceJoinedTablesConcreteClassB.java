package entities.inheritance.joined;

import javax.persistence.*;

@Entity
@Table(name = "IerarhieJoinedB")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class InheritanceJoinedTablesConcreteClassB extends InheritanceJoinedTablesSuperClass {

    @Column(nullable = false)
    private String specificB;

    public String getSpecificB() {
        return specificB;
    }

    public void setSpecificB(String specificB) {
        this.specificB = specificB;
    }

}
