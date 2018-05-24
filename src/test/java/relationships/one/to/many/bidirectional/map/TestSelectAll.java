package relationships.one.to.many.bidirectional.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.HashMap;
import java.util.List;

public class TestSelectAll extends TransactionalSetup {

    private ParentMap model = buildModel();

    private ParentMap buildModel() {

        ParentMap parent = new ParentMap();
        parent.setId(1L);
        parent.setName("parent 1");
        parent.setChildren(new HashMap<>());

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
        em.persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        List<ParentMap> parents = em.createQuery("select t from ParentMap t", ParentMap.class).getResultList();

        Assert.assertEquals(1, parents.size());
        ReflectionAssert.assertReflectionEquals(model, parents.get(0), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(model.getChildren(), parents.get(0).getChildren(), ReflectionComparatorMode.LENIENT_ORDER);

    }
}
