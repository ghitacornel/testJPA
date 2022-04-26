package relationships.manytomany.bothowners;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import javax.persistence.PersistenceException;

public class TestPersist extends TransactionalSetup {

    BothOwnerM m;
    BothOwnerN n1;
    BothOwnerN n2;

    @BeforeEach
    public void buildModel() {

        m = new BothOwnerM();
        m.setId(1);
        m.setName("m 1 name");

        n1 = new BothOwnerN();
        n1.setId(1);
        n1.setName("n 1 name");

        n2 = new BothOwnerN();
        n2.setId(2);
        n2.setName("n 2 name");

        n1.getListWithMs().add(m);
        n2.getListWithMs().add(m);
        m.getListWithNs().add(n1);
        m.getListWithNs().add(n2);

    }

    @Test
    public void testPersistOnlyOneSideAndFail() {
        Assertions.assertThrows(PersistenceException.class, () -> {
            em.persist(m);
            flushAndClear();
        });
    }

    @Test
    public void testPersistPartiallyAndFail() {
        Assertions.assertThrows(PersistenceException.class, () -> {
            em.persist(m);
            em.persist(n1);
            flushAndClear();
        });
    }

    @Test
    public void testPersistAllAndFail() {
        Assertions.assertThrows(PersistenceException.class, () -> {
            em.persist(m);
            em.persist(n1);
            em.persist(n2);
            flushAndClear();
        });
    }

    /**
     * This kind of mapping doesn't work as expected
     */
    @Test
    public void testPersistFirstEntitiesThenOnly1SideOfTheRelationshipAndOK() {

        m.getListWithNs().clear();
        n1.getListWithMs().clear();
        n2.getListWithMs().clear();

        // persist all
        em.persist(m);
        em.persist(n1);
        em.persist(n2);
        em.flush();

        m.getListWithNs().add(n1);
        m.getListWithNs().add(n2);
        // DO NOT SET counterpart references
        flushAndClear();

        // verify
        {// adjust the model to reflect expected changes
            n1.getListWithMs().add(m);
            n2.getListWithMs().add(m);
            em.flush();
        }
        org.assertj.core.api.Assertions.assertThat(m).usingRecursiveComparison().isEqualTo(em.find(BothOwnerM.class, m.getId()));
        org.assertj.core.api.Assertions.assertThat(n1).usingRecursiveComparison().isEqualTo(em.find(BothOwnerN.class, n1.getId()));
        org.assertj.core.api.Assertions.assertThat(n2).usingRecursiveComparison().isEqualTo(em.find(BothOwnerN.class, n2.getId()));

    }

}