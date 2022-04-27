package inheritance.joined;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "IerarhieJoinedA")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class InheritanceJoinedTablesConcreteClassA extends InheritanceJoinedTablesSuperClass {

    /**
     * not null columns can be specified in this strategy case
     */
    @Column(nullable = false)
    private String specificA;

    public String getSpecificA() {
        return specificA;
    }

    public void setSpecificA(String specificA) {
        this.specificA = specificA;
    }

}
