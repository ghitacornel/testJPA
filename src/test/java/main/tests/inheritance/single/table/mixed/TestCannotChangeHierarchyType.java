package main.tests.inheritance.single.table.mixed;

import entities.inheritance.mixed.ConcreteClassB;
import entities.inheritance.mixed.MixedContainer;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

public class TestCannotChangeHierarchyType extends TransactionalSetup {

    private MixedContainer model = buildInitialModel();

    static MixedContainer buildInitialModel() {

        MixedContainer container = new MixedContainer();
        container.setName("concrete container name");

        {
            ConcreteClassB concreteClassB1 = new ConcreteClassB();
            concreteClassB1.setName("concrete class B 1");
            concreteClassB1.setDiscriminator("B");
            ConcreteClassB concreteClassB2 = new ConcreteClassB();
            concreteClassB2.setName("concrete class B 2");
            concreteClassB2.setDiscriminator("B");
            container.getConcreteClassBs().add(concreteClassB1);
            container.getConcreteClassBs().add(concreteClassB2);
        }

        container.getConcreteSuperClass().addAll(container.getConcreteClassBs());
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

        ReflectionAssert.assertReflectionEquals(model, containerInitial, ReflectionComparatorMode.LENIENT_ORDER);

        containerInitial.getConcreteClassBs().get(1).setDiscriminator("A");
        em.merge(containerInitial.getConcreteClassBs().get(1));
        flushAndClear();

        MixedContainer containerFinal = em.createQuery("select t from MixedContainer t", MixedContainer.class).getSingleResult();

        Assert.assertEquals(2, containerFinal.getConcreteSuperClass().size());

        // should be 1 but hierarchy cannot be changed
        Assert.assertEquals(2, containerFinal.getConcreteClassBs().size());
        Assert.assertEquals(0, containerFinal.getConcreteClassAs().size());

    }

}
