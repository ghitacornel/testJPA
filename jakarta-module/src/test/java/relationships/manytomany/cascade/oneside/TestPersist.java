package relationships.manytomany.cascade.oneside;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

/**
 * only 1 test is enough to prove the changes are propagated one way only
 */
public class TestPersist extends TransactionalSetup {

    CascadeOneSideM m1;
    CascadeOneSideN n1;
    CascadeOneSideN n2;

    @BeforeEach
    public void buildModel() {

        m1 = new CascadeOneSideM();
        m1.setId(1);
        m1.setName("m 1 name");

        n1 = new CascadeOneSideN();
        n1.setId(1);
        n1.setName("n 1 name");

        n2 = new CascadeOneSideN();
        n2.setId(2);
        n2.setName("n 2 name");

        n1.getListWithMs().add(m1);
        n2.getListWithMs().add(m1);
        m1.getListWithNs().add(n1);
        m1.getListWithNs().add(n2);

    }

    @Test
    public void testPersistFromTheOwningSide() {

        // persist and expect cascade
        em.persist(m1);
        flushAndClear();

        // verify all is persisted
        org.assertj.core.api.Assertions.assertThat(m1).usingRecursiveComparison().isEqualTo(em.find(CascadeOneSideM.class, m1.getId()));
        org.assertj.core.api.Assertions.assertThat(n1).usingRecursiveComparison().isEqualTo(em.find(CascadeOneSideN.class, n1.getId()));
        org.assertj.core.api.Assertions.assertThat(n2).usingRecursiveComparison().isEqualTo(em.find(CascadeOneSideN.class, n2.getId()));

    }

    @Test
    public void testPersistFromTheNonOwningSide() {

        // persist and expect no cascade
        em.persist(n1);
        flushAndClear();

        // verify some parts of the model are persisted
        {// adjust model to reflect expected
            n1.getListWithMs().clear();
        }
        Assertions.assertNull(em.find(CascadeOneSideM.class, m1.getId()));
        org.assertj.core.api.Assertions.assertThat(n1).usingRecursiveComparison().isEqualTo(em.find(CascadeOneSideN.class, n1.getId()));
        Assertions.assertNull(em.find(CascadeOneSideM.class, n2.getId()));

    }

}