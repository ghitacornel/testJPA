package relationships.collections.maps.onetomany;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.List;

public class TestSelectAll extends TransactionalSetup {

    private final ParentMap parent = buildModel();

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

    @BeforeEach
    public void before() {
        persist(parent, parent.getChildren().values());
        flushAndClear();
    }

    @Test
    public void test() {

        List<ParentMap> parents = em.createQuery("select t from ParentMap t", ParentMap.class).getResultList();

        Assertions.assertEquals(1, parents.size());
        ReflectionAssert.assertReflectionEquals(parent, parents.get(0), ReflectionComparatorMode.LENIENT_ORDER);

    }
}
