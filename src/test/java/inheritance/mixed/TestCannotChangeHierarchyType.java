package inheritance.mixed;

import entities.inheritance.mixed.ConcreteClassA;
import entities.inheritance.mixed.ConcreteClassB;
import entities.inheritance.mixed.MixedContainer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestCannotChangeHierarchyType extends TransactionalSetup {

    private MixedContainer model = buildInitialModel();

    private static MixedContainer buildInitialModel() {

        MixedContainer container = new MixedContainer();
        container.setName("concrete container name");

        {
            ConcreteClassB concreteClassB1 = new ConcreteClassB();
            concreteClassB1.setName("concrete class B 1");
            container.getConcreteClassBs().add(concreteClassB1);
            ConcreteClassB concreteClassB2 = new ConcreteClassB();
            concreteClassB2.setName("concrete class B 2");
            container.getConcreteClassBs().add(concreteClassB2);
        }
        {
            ConcreteClassA concreteClassA1 = new ConcreteClassA();
            concreteClassA1.setName("concrete class A 1");
            container.getConcreteClassAs().add(concreteClassA1);
        }

        container.getConcreteSuperClass().addAll(container.getConcreteClassBs());
        container.getConcreteSuperClass().addAll(container.getConcreteClassAs());
        return container;
    }

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        MixedContainer containerInitial = em.createQuery("select t from MixedContainer t", MixedContainer.class).getSingleResult();

        // discriminators are set automatically so in order to check the data models we need to ensure initial model has them set properly
        for (ConcreteClassB concreteClassB : model.getConcreteClassBs()) {
            concreteClassB.setDiscriminator("B");
        }
        for (ConcreteClassA concreteClassA : model.getConcreteClassAs()) {
            concreteClassA.setDiscriminator("A");
        }
        ReflectionAssert.assertReflectionEquals(model, containerInitial, ReflectionComparatorMode.LENIENT_ORDER);

        // try to change an instance hierarchic type
        containerInitial.getConcreteClassBs().get(1).setDiscriminator("A");
        em.merge(containerInitial.getConcreteClassBs().get(1));
        flushAndClear();

        // verify changed type operation has no success
        MixedContainer containerFinal = em.createQuery("select t from MixedContainer t", MixedContainer.class).getSingleResult();
        Assert.assertEquals(3, containerFinal.getConcreteSuperClass().size());

        // should be 1 but hierarchy cannot be changed
        Assert.assertEquals(2, containerFinal.getConcreteClassBs().size());
        Assert.assertEquals(1, containerFinal.getConcreteClassAs().size());

    }

}
