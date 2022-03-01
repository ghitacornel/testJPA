package relationships.collections.sets.onetomany;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.List;

public class TestSelectAll extends TransactionalSetup {

    private final ParentSet parent = buildModel();

    private ParentSet buildModel() {

        ParentSet parent = new ParentSet();
        parent.setId(1L);
        parent.setName("parent 1");

        for (long i = 1; i <= 3; i++) {
            ChildSet child = new ChildSet();
            child.setId(i);
            child.setName("child " + i);
            child.setParent(parent);
            parent.getChildren().add(child);
        }

        return parent;
    }

    @BeforeEach
    public void before() {
        persist(parent, parent.getChildren());
        flushAndClear();
    }

    @Test
    public void test() {

        List<ParentSet> parents = em.createQuery("select t from ParentSet t", ParentSet.class).getResultList();

        Assertions.assertEquals(1, parents.size());
        ReflectionAssert.assertReflectionEquals(parent, parents.get(0), ReflectionComparatorMode.LENIENT_ORDER);

    }
}
