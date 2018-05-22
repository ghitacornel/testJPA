package entities.inheritance.mixed;

import entities.inheritance.InheritanceMappedSuperClass;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MixedContainer extends InheritanceMappedSuperClass {

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reference_id")
    private List<ConcreteSuperClass> concreteSuperClass = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ConcreteClassA.class)
    @JoinColumn(name = "reference_id_a")
    private List<ConcreteClassA> concreteClassAs = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ConcreteClassB.class)
    @JoinColumn(name = "reference_id_b")
    private List<ConcreteClassB> concreteClassBs = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ConcreteClassB> getConcreteClassBs() {
        return concreteClassBs;
    }

    public void setConcreteClassBs(List<ConcreteClassB> concreteClassBs) {
        this.concreteClassBs = concreteClassBs;
    }

    public List<ConcreteSuperClass> getConcreteSuperClass() {
        return concreteSuperClass;
    }

    public void setConcreteSuperClass(List<ConcreteSuperClass> concreteSuperClass) {
        this.concreteSuperClass = concreteSuperClass;
    }

    public List<ConcreteClassA> getConcreteClassAs() {
        return concreteClassAs;
    }

    public void setConcreteClassAs(List<ConcreteClassA> concreteClassAs) {
        this.concreteClassAs = concreteClassAs;
    }
}
