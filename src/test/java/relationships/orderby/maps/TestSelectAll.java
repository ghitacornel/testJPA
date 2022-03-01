package relationships.orderby.maps;

import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.*;

public class TestSelectAll extends TransactionalSetup {

    OTOMOrderMapParent parent;

    private OTOMOrderMapParent buildModel() {

        OTOMOrderMapParent parent = new OTOMOrderMapParent();
        parent.setId(1);
        parent.setName("parent");

        OTOMOrderMapChild child1 = new OTOMOrderMapChild();
        child1.setId(1);
        child1.setName("child A");

        OTOMOrderMapChild child2 = new OTOMOrderMapChild();
        child2.setId(2);
        child2.setName("child B");

        parent.getChildren().put(2, child2);
        parent.getChildren().put(1, child1);

        return parent;
    }

    @Test
    public void testSelectAllInOrder() {

        // create model
        parent = buildModel();

        persist(parent);
        flushAndClear();

        // verify order
        OTOMOrderMapParent expected = buildModel();
        {// adjust model to expected result

            List<OTOMOrderMapChild> children = new ArrayList<>(expected.getChildren().values());
            expected.getChildren().clear();

            children.sort(Comparator.comparing(OTOMOrderMapChild::getName));
            for (OTOMOrderMapChild child : children) {
                expected.getChildren().put(child.getId(), child);
            }

        }

        ReflectionAssert.assertReflectionEquals(expected, em.find(OTOMOrderMapParent.class, parent.getId()));

    }
}
