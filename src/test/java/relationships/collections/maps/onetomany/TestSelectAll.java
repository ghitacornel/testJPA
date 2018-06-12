package relationships.collections.maps.onetomany;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import relationships.collections.maps.onetomany.ChildMap;
import relationships.collections.maps.onetomany.ParentMap;
import setup.TransactionalSetup;

import java.util.List;

public class TestSelectAll extends TransactionalSetup {

    private ParentMap parent = buildModel();

    private ParentMap buildModel() {

        ParentMap parent = new ParentMap();
        parent.setId(1L);
        parent.setName("parent 1");

        for (long i = 1; i <= 3; i++) {
            ChildMap child = new ChildMap();
            child.setId(i);
            child.setName("child " + i);
            child.setParent(parent);
            parent.getChildren().put(child.getId(), child);
        }

        return parent;
    }

    @Before
    public void before() {
        persist(parent, parent.getChildren().values());
        flushAndClear();
    }

    @Test
    public void test() {

        List<ParentMap> parents = em.createQuery("select t from ParentMap t", ParentMap.class).getResultList();

        Assert.assertEquals(1, parents.size());
        ReflectionAssert.assertReflectionEquals(parent, parents.get(0), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(parent.getChildren(), parents.get(0).getChildren(), ReflectionComparatorMode.LENIENT_ORDER);

    }
}
