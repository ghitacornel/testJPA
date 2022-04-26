package relationships.manytomany.cascade.bothways;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestRemoveLink extends TransactionalSetup {

    CascadeBothWaysM m;
    CascadeBothWaysN n;

    @BeforeEach
    public void buildModel() {

        m = new CascadeBothWaysM();
        m.setId(1);
        m.setName("m 1 name");

        n = new CascadeBothWaysN();
        n.setId(1);
        n.setName("n 1 name");

        n.getListWithMs().add(m);
        m.getListWithNs().add(n);

        persist(m);
        flushAndClear();
    }

    @Test
    public void testRemoveOfLinkFromTheBothSides() {

        // remove
        CascadeBothWaysM existingM = em.find(CascadeBothWaysM.class, m.getId());
        CascadeBothWaysN existingN = em.find(CascadeBothWaysN.class, n.getId());
        existingM.getListWithNs().remove(existingN);
        existingN.getListWithMs().remove(existingM);
        flushAndClear();

        // verify removal of link
        {// adjust model to reflect the expected result
            m.getListWithNs().remove(n);
            n.getListWithMs().remove(m);
        }
        org.assertj.core.api.Assertions.assertThat(m).usingRecursiveComparison().isEqualTo(em.find(CascadeBothWaysM.class, m.getId()));
        org.assertj.core.api.Assertions.assertThat(n).usingRecursiveComparison().isEqualTo(em.find(CascadeBothWaysN.class, n.getId()));

    }

    @Test
    public void testRemoveOfLinkFromTheOwningSideOnlyIsNotWorking() {

        // remove
        em.find(CascadeBothWaysM.class, m.getId()).getListWithNs().remove(n);
        flushAndClear();

        // verify removal of link not working
        org.assertj.core.api.Assertions.assertThat(m).usingRecursiveComparison().isEqualTo(em.find(CascadeBothWaysM.class, m.getId()));
        org.assertj.core.api.Assertions.assertThat(n).usingRecursiveComparison().isEqualTo(em.find(CascadeBothWaysN.class, n.getId()));

    }

    @Test
    public void testRemoveOfLinkFromTheNonOwningSideOnlyIsNotWorking() {

        // remove
        em.find(CascadeBothWaysM.class, n.getId()).getListWithNs().remove(m);
        flushAndClear();

        // verify removal of link not working
        org.assertj.core.api.Assertions.assertThat(m).usingRecursiveComparison().isEqualTo(em.find(CascadeBothWaysM.class, m.getId()));
        org.assertj.core.api.Assertions.assertThat(n).usingRecursiveComparison().isEqualTo(em.find(CascadeBothWaysN.class, n.getId()));

    }

}