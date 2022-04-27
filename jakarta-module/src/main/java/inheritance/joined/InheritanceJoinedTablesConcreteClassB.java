package inheritance.joined;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "IerarhieJoinedB")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class InheritanceJoinedTablesConcreteClassB extends InheritanceJoinedTablesSuperClass {

    /**
     * not null columns can be specified in this strategy case
     */
    @Column(nullable = false)
    private String specificB;

    public String getSpecificB() {
        return specificB;
    }

    public void setSpecificB(String specificB) {
        this.specificB = specificB;
    }

}
