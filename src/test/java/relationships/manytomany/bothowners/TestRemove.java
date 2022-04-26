package relationships.manytomany.bothowners;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.Collections;

public class TestRemove extends TransactionalSetup {

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
        flushAndClear();

    }

    @Test
    public void testRemoveFrom1SideWorks() {

        n1.getListWithMs().add(m);
        n2.getListWithMs().add(m);

        BothOwnerM m1 = em.find(BothOwnerM.class, m.getId());
        org.assertj.core.api.Assertions.assertThat(m).usingRecursiveComparison().isEqualTo(m1);

        m1.getListWithNs().remove(em.find(BothOwnerN.class, n1.getId()));
        flushAndClear();

        BothOwnerM m2 = em.find(BothOwnerM.class, m.getId());
        m.getListWithNs().remove(n1);
        org.assertj.core.api.Assertions.assertThat(m).usingRecursiveComparison().isEqualTo(m2);
    }

    @Test
    public void testSetProxyDoesNotKeepsOldOnes() {

        n1.getListWithMs().add(m);
        n2.getListWithMs().add(m);

        BothOwnerM m1 = em.find(BothOwnerM.class, m.getId());
        org.assertj.core.api.Assertions.assertThat(m).usingRecursiveComparison().isEqualTo(m1);

        BothOwnerN n3 = new BothOwnerN();
        n3.setId(3);
        n3.setName("n 3 name");
        em.persist(n3);

        em.find(BothOwnerM.class, m.getId()).setListWithNs(Collections.singletonList(n3));// set list to 1
        flushAndClear();

        BothOwnerM m2 = em.find(BothOwnerM.class, m.getId());
        m.getListWithNs().add(n3);
        n3.getListWithMs().add(m);
        m.getListWithNs().remove(n1);
        m.getListWithNs().remove(n2);
        org.assertj.core.api.Assertions.assertThat(m).usingRecursiveComparison().isEqualTo(m2);
    }

}