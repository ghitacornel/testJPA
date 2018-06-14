package relationships.orderby.sets;

import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestSelectAll extends TransactionalSetup {

    @Test
    public void testSelectAllInOrder() {

        // create model
        OTOMOrderSetParent parent = new OTOMOrderSetParent();
        parent.setId(1);
        parent.setName("parent");

        OTOMOrderSetChild child1 = new OTOMOrderSetChild();
        child1.setId(1);
        child1.setName("child1");

        OTOMOrderSetChild child2 = new OTOMOrderSetChild();
        child2.setId(2);
        child2.setName("child2");

        parent.getChildren().add(child2);
        parent.getChildren().add(child1);

        persist(parent);
        flushAndClear();

        // verify order
        {// adjust model to expected result
            List<OTOMOrderSetChild> children = new ArrayList<>(parent.getChildren());
            Collections.reverse(children);
            parent.getChildren().clear();
            parent.getChildren().addAll(children);
        }

        ReflectionAssert.assertReflectionEquals(parent, em.find(OTOMOrderSetParent.class, parent.getId()));

    }
}
