package relationships.onetomany.strict;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestLoadParentAndChildrenInOneNamedQuery extends
        TransactionalSetup {

    private OTOMStrictParent parent = buildModel();

    private OTOMStrictParent buildModel() {

        OTOMStrictParent parent = new OTOMStrictParent();
        parent.setId(1);
        parent.setName("parent name");

        for (int i = 1; i <= 3; i++) {
            OTOMStrictChild child = new OTOMStrictChild();
            child.setId(i);
            child.setName("child " + i);
            child.setParent(parent);
            parent.getChildren().add(child);
        }

        return parent;
    }

    @Before
    public void before() {
        em.persist(parent);
        flushAndClear();
    }

    @Test
    public void test() {
        OTOMStrictParent existing = em.createQuery("select p from OTOMStrictParent p join fetch p.children where p.id = ?1", OTOMStrictParent.class).
                setParameter(1, parent.getId()).
                getSingleResult();
        ReflectionAssert.assertReflectionEquals(parent, existing, ReflectionComparatorMode.LENIENT_ORDER);
    }
}
