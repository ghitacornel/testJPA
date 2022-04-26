package inheritance.change;

import inheritance.InheritanceMappedSuperClass;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an entity container of other entities belonging to the same class hierarchy
 */
@Entity
public class MixedContainer extends InheritanceMappedSuperClass {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reference_id")
    final private List<ConcreteSuperClass> concreteSuperClass = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, targetEntity = ConcreteClassA.class)
    @JoinColumn(name = "reference_id_a")
    final private List<ConcreteClassA> concreteClassAs = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, targetEntity = ConcreteClassB.class)
    @JoinColumn(name = "reference_id_b")
    final private List<ConcreteClassB> concreteClassBs = new ArrayList<>();
    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ConcreteClassB> getConcreteClassBs() {
        return concreteClassBs;
    }

    public List<ConcreteSuperClass> getConcreteSuperClass() {
        return concreteSuperClass;
    }

    public List<ConcreteClassA> getConcreteClassAs() {
        return concreteClassAs;
    }

}
