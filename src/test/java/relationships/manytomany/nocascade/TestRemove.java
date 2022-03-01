package relationships.manytomany.nocascade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import javax.persistence.PersistenceException;

public class TestRemove extends TransactionalSetup {

    NoCascadeM m;
    NoCascadeN n;

    @BeforeEach
    public void before() {

        n = new NoCascadeN();
        n.setId(1);
        n.setName("n 1 name");

        m = new NoCascadeM();
        m.setId(1);
        m.setName("m 1 name");

        n.getListWithMs().add(m);
        m.getListWithNs().add(n);

        persist(m);
        persist(n);
        flushAndClear();
    }

    @Test
    public void testRemoveTheOwningSide() {

        // remove
        em.remove(em.find(NoCascadeM.class, m.getId()));
        flushAndClear();

        // observe no update of relationship is required on the not owning side
        // best practice is to update the not owning side also

        // verify final
        {// adjust model to reflect expected changes
            n.getListWithMs().remove(m);
        }
        Assertions.assertNull(em.find(NoCascadeM.class, m.getId()));
        ReflectionAssert.assertReflectionEquals(n, em.find(NoCascadeN.class, n.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testRemoveTheNonOwningSideWorksOnlyWhenOwningSideLinksAreRemovedAlso() {

        // remove links to not owning side first
        NoCascadeN existingN = em.find(NoCascadeN.class, n.getId());
        for (NoCascadeM existingM : existingN.getListWithMs()) {
            existingM.getListWithNs().remove(existingN);
        }

        // remove not owning side second
        em.remove(existingN);
        flushAndClear();

        // verify final
        {// adjust model to reflect expected changes
            m.getListWithNs().remove(n);
        }
        Assertions.assertNull(em.find(NoCascadeN.class, n.getId()));
        ReflectionAssert.assertReflectionEquals(m, em.find(NoCascadeM.class, m.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testRemoveTheNonOwningSideOnlyNotWorking() {
        Assertions.assertThrows(PersistenceException.class, () -> {
            em.remove(em.find(NoCascadeN.class, n.getId()));
            flushAndClear();
        });
    }

}