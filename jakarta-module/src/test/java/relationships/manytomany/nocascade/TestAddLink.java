package relationships.manytomany.nocascade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestAddLink extends TransactionalSetup {

    NoCascadeN n;
    NoCascadeM m;

    @BeforeEach
    public void before() {

        verifyCorrespondingTableIsEmpty(NoCascadeM.class);
        verifyCorrespondingTableIsEmpty(NoCascadeN.class);

        n = new NoCascadeN();
        n.setId(1);
        n.setName("n 1 name");

        m = new NoCascadeM();
        m.setId(1);
        m.setName("m 1 name");

        persist(m);
        persist(n);
        flushAndClear();

    }

    @Test
    public void testAddLinkOwningSide() {

        NoCascadeM existingM = em.find(NoCascadeM.class, m.getId());
        NoCascadeN existingN = em.find(NoCascadeN.class, m.getId());

        // add link
        existingM.getListWithNs().add(existingN);
        flushAndClear();

        // verify add link
        {// adjust model to reflect expected changes
            n.getListWithMs().add(m);
            m.getListWithNs().add(n);
        }
        org.assertj.core.api.Assertions.assertThat(m).usingRecursiveComparison().isEqualTo(em.find(NoCascadeM.class, m.getId()));
        org.assertj.core.api.Assertions.assertThat(n).usingRecursiveComparison().isEqualTo(em.find(NoCascadeN.class, n.getId()));

    }

    @Test
    public void testAddLinkNotOwningSideDoesNotWork() {

        NoCascadeM existingM = em.find(NoCascadeM.class, m.getId());
        NoCascadeN existingN = em.find(NoCascadeN.class, m.getId());

        // add link
        existingN.getListWithMs().add(existingM);
        flushAndClear();

        // verify link was not added
        org.assertj.core.api.Assertions.assertThat(m).usingRecursiveComparison().isEqualTo(em.find(NoCascadeM.class, m.getId()));
        org.assertj.core.api.Assertions.assertThat(n).usingRecursiveComparison().isEqualTo(em.find(NoCascadeN.class, n.getId()));

    }

    @Test
    public void testAddLinkBothSides() {

        NoCascadeM existingM = em.find(NoCascadeM.class, m.getId());
        NoCascadeN existingN = em.find(NoCascadeN.class, m.getId());

        // add link
        existingN.getListWithMs().add(existingM);
        existingM.getListWithNs().add(existingN);
        flushAndClear();

        // verify add link
        {// adjust model to reflect expected changes
            n.getListWithMs().add(m);
            m.getListWithNs().add(n);
        }
        org.assertj.core.api.Assertions.assertThat(m).usingRecursiveComparison().isEqualTo(em.find(NoCascadeM.class, m.getId()));
        org.assertj.core.api.Assertions.assertThat(n).usingRecursiveComparison().isEqualTo(em.find(NoCascadeN.class, n.getId()));

    }

}