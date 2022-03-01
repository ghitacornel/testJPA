package relationships.onetomany.notstrict;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestSetProxyToNewList extends TransactionalSetup {

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

    @BeforeEach
    public void before() {
        em.persist(parent);
        persist(parent.getChildren());
        flushAndClear();
    }

    @Test
    public void testSetProxyDoesNotRemoveExistingChildren() {
        OTOMNotStrictParent parent1 = em.find(OTOMNotStrictParent.class, parent.getId());
        ReflectionAssert.assertReflectionEquals(parent, parent1);

        List<OTOMNotStrictChild> newChildren = new ArrayList<>();
        OTOMNotStrictChild child4 = new OTOMNotStrictChild();
        child4.setId(4);
        child4.setName("child " + 4);
        child4.setParent(parent);
        newChildren.add(child4);
        OTOMNotStrictChild child5 = new OTOMNotStrictChild();
        child5.setId(5);
        child5.setName("child " + 5);
        child5.setParent(parent);
        newChildren.add(child5);

        em.persist(child4);
        em.persist(child5);
        parent1.setChildren(newChildren);// set proxy to a new list
        flushAndClear();

        OTOMNotStrictParent parent2 = em.find(OTOMNotStrictParent.class, parent.getId());
        // observe all old children are still present along with the new children
        parent.getChildren().addAll(newChildren);
        ReflectionAssert.assertReflectionEquals(parent, parent2);

    }
}
