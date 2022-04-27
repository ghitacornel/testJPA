package relationships.orderby.manytomany;

import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.Collections;

public class TestSelectAll extends TransactionalSetup {

    @Test
    public void testSelectAllInOrder() {

        // create model
        MTOMOrderM m = new MTOMOrderM();
        m.setId(1);
        m.setName("m");

        MTOMOrderN n1 = new MTOMOrderN();
        n1.setId(1);
        n1.setName("n1");

        MTOMOrderN n2 = new MTOMOrderN();
        n2.setId(2);
        n2.setName("n2");

        m.getListWithNs().add(n2);
        m.getListWithNs().add(n1);

        persist(m);
        flushAndClear();

        // verify order
        {// adjust model to expected result
            Collections.reverse(m.getListWithNs());
        }

        org.assertj.core.api.Assertions.assertThat(m).usingRecursiveComparison().isEqualTo(em.find(MTOMOrderM.class, m.getId()));

    }
}
