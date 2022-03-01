package relationships.onetomany.notstrict.cascade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestOneToManyNotStrictCascade extends TransactionalSetup {

    @Test
    public void testPersistRemoveCascade() {

        OTOMNotStrictCascadeParent parent = new OTOMNotStrictCascadeParent();
        parent.setId(1);
        parent.setName("parent");

        OTOMNotStrictCascadeChild child1 = new OTOMNotStrictCascadeChild();
        child1.setId(1);
        child1.setName("child1");
        child1.setParent(parent);
        parent.getChildren().add(child1);

        OTOMNotStrictCascadeChild child2 = new OTOMNotStrictCascadeChild();
        child2.setId(2);
        child2.setName("child2");
        child2.setParent(parent);
        parent.getChildren().add(child2);

        em.persist(parent);
        flushAndClear();

        ReflectionAssert.assertReflectionEquals(em.find(OTOMNotStrictCascadeParent.class, 1), parent, ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(em.find(OTOMNotStrictCascadeChild.class, 1), child1, ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(em.find(OTOMNotStrictCascadeChild.class, 2), child2, ReflectionComparatorMode.LENIENT_ORDER);
        flushAndClear();

        // NO CASCADE ON DELETE => remove links first, both ways
        OTOMNotStrictCascadeParent parent1 = em.find(OTOMNotStrictCascadeParent.class, 1);
        parent1.getChildren().forEach(otomNotStrictCascadeChild -> otomNotStrictCascadeChild.setParent(null));
        parent1.getChildren().clear();
        em.remove(parent1);
        flushAndClear();

        Assertions.assertNull(em.find(OTOMNotStrictCascadeParent.class, 1));
        Assertions.assertNotNull(em.find(OTOMNotStrictCascadeChild.class, 1));
        Assertions.assertNotNull(em.find(OTOMNotStrictCascadeChild.class, 2));

    }

}
