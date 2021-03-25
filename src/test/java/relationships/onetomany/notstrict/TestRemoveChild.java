package relationships.onetomany.notstrict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestRemoveChild extends TransactionalSetup {

    private final OTOMNotStrictParent parent = buildModel();

    private OTOMNotStrictParent buildModel() {

        OTOMNotStrictParent parent = new OTOMNotStrictParent();
        parent.setId(1);
        parent.setName("parent name");

        for (int i = 1; i <= 3; i++) {
            OTOMNotStrictChild child = new OTOMNotStrictChild();
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
        persist(parent.getChildren());
        flushAndClear();
    }

    @Test
    public void testRemoveChild() {

        em.remove(em.find(OTOMNotStrictChild.class, 1));
        flushAndClear();

        // test parent and other children are unaffected
        OTOMNotStrictParent expectedParent = buildModel();
        {// adjust model to reflect expected changes
            expectedParent.getChildren().remove(0);
        }
        ReflectionAssert.assertReflectionEquals(expectedParent, em.find(OTOMNotStrictParent.class, expectedParent.getId()), ReflectionComparatorMode.LENIENT_ORDER);

        // test child is now removed
        Assert.assertNull(em.find(OTOMNotStrictChild.class, 1));

    }

    @Test
    public void testRemoveChildWhenParentAndSiblingsAreLoadedWorksAndDoesNotAffectListOfSiblings() {

        OTOMNotStrictParent existingParent = em.find(OTOMNotStrictParent.class, parent.getId());
        Assert.assertEquals(3, existingParent.getChildren().size());// verify number of children

        em.remove(em.find(OTOMNotStrictChild.class, 1));
        Assert.assertEquals(3, existingParent.getChildren().size());// verify number of children is the same
        flushAndClear();

        // test parent and other children are unaffected
        OTOMNotStrictParent expectedParent = buildModel();
        {// adjust model to reflect expected changes
            expectedParent.getChildren().remove(0);
        }
        ReflectionAssert.assertReflectionEquals(expectedParent, em.find(OTOMNotStrictParent.class, expectedParent.getId()), ReflectionComparatorMode.LENIENT_ORDER);

        // test child is now removed
        Assert.assertNull(em.find(OTOMNotStrictChild.class, 1));

    }

}
