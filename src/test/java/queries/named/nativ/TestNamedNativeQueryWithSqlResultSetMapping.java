package queries.named.nativ;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import relationships.onetomany.strict.OTOMStrictChild;
import relationships.onetomany.strict.OTOMStrictParent;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestNamedNativeQueryWithSqlResultSetMapping extends TransactionalSetup {

    private OTOMStrictParent model = buildModel();

    private OTOMStrictParent buildModel() {

        OTOMStrictParent parent = new OTOMStrictParent();
        parent.setId(1);
        parent.setName("parent " + parent.getId());

        for (int i = 1; i <= 3; i++) {
            OTOMStrictChild child = new OTOMStrictChild();
            child.setId(i);
            child.setName("child " + +child.getId());
            child.setParent(parent);
            parent.getChildren().add(child);
        }

        return parent;
    }

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test() {

        // fetch
        List<Object[]> list = em.createNamedQuery("OTOMStrictParent.findWithChildrenNative").getResultList();

        // verify, order is ignored
        List<OTOMStrictChild> fetchedChildren = new ArrayList<>();
        for (Object[] objects : list) {

            // parent is 'retrieved' many times
            OTOMStrictParent parent = (OTOMStrictParent) objects[0];
            OTOMStrictChild child = (OTOMStrictChild) objects[1];
            fetchedChildren.add(child);

            Assert.assertEquals(model.getId(), parent.getId());
            Assert.assertEquals(model.getName(), parent.getName());

        }

        // verify fetched children
        ReflectionAssert.assertReflectionEquals(model.getChildren(), fetchedChildren, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFullyFetchedEntitiesAreManaged() {

        // fetch
        List<Object[]> existing = em.createNamedQuery("OTOMStrictParent.findWithChildrenNative").getResultList();

        // alter
        for (Object[] objects : existing) {
            OTOMStrictParent parent = (OTOMStrictParent) objects[0];
            parent.setName("new parent name " + parent.getId());
            OTOMStrictChild child = (OTOMStrictChild) objects[1];
            child.setName("new child name " + child.getId());
        }

        flushAndClear();

        // fetch again
        List<Object[]> updated = em.createNamedQuery("OTOMStrictParent.findWithChildrenNative").getResultList();

        // adjust model for verification
        {
            model.setName("new parent name " + model.getId());
            for (OTOMStrictChild child : model.getChildren()) {
                child.setName("new child name " + child.getId());
            }

        }
        // verify, order is ignored
        List<OTOMStrictChild> fetchedChildren = new ArrayList<>();
        for (Object[] objects : updated) {

            // parent is 'retrieved' many times
            OTOMStrictParent parent = (OTOMStrictParent) objects[0];
            OTOMStrictChild child = (OTOMStrictChild) objects[1];
            fetchedChildren.add(child);

            Assert.assertEquals(model.getId(), parent.getId());
            Assert.assertEquals(model.getName(), parent.getName());

        }

        // verify fetched children
        ReflectionAssert.assertReflectionEquals(model.getChildren(), fetchedChildren, ReflectionComparatorMode.LENIENT_ORDER);


    }

}
