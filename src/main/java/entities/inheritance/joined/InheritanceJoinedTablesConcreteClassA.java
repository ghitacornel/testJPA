package entities.inheritance.joined;

import javax.persistence.*;

@Entity
@Table(name = "IerarhieJoinedA")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class InheritanceJoinedTablesConcreteClassA extends InheritanceJoinedTablesSuperClass {

    @Column(nullable = false)
    private String specificA;

    public String getSpecificA() {
        return specificA;
    }

    public void setSpecificA(String specificA) {
        this.specificA = specificA;
    }

}
