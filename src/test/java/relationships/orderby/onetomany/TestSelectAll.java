package relationships.orderby.onetomany;

import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.Collections;

public class TestSelectAll extends TransactionalSetup {

    @Test
    public void testSelectAllInOrder() {

        // create model
        OTOMOrderParent parent = new OTOMOrderParent();
        parent.setId(1);
        parent.setName("parent");

        OTOMOrderChild child1 = new OTOMOrderChild();
        child1.setId(1);
        child1.setName("child1");

        OTOMOrderChild child2 = new OTOMOrderChild();
        child2.setId(2);
        child2.setName("child2");

        parent.getChildren().add(child2);
        parent.getChildren().add(child1);

        persist(parent);
        flushAndClear();

        // verify order
        {// adjust model to expected result
            Collections.reverse(parent.getChildren());
        }

        ReflectionAssert.assertReflectionEquals(parent, em.find(OTOMOrderParent.class, parent.getId()));

    }
}
