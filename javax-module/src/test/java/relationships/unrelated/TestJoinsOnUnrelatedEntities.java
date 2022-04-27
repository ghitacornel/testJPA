package relationships.unrelated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.Collections;
import java.util.List;

public class TestJoinsOnUnrelatedEntities extends TransactionalSetup {

    @Test
    public void testInnerJoin() {

        EntityLeft entityLeft1 = new EntityLeft(1, "1");
        EntityRight entityRight1 = new EntityRight(1, "1");
        EntityRight entityRight2 = new EntityRight(2, "1");

        EntityLeft entityLeft2 = new EntityLeft(2, "2");
        EntityRight entityRight3 = new EntityRight(3, "2");

        EntityLeft entityLeft3 = new EntityLeft(3, "3");
        EntityRight entityRight4 = new EntityRight(4, "4");

        EntityRight entityRight5 = new EntityRight(5, "5");

        persist(entityLeft1, entityLeft2, entityLeft3, entityRight1, entityRight2, entityRight3, entityRight4, entityRight5);

        List<UnrelatedPair> list = em.createQuery("select new relationships.unrelated.UnrelatedPair(t1.id,t2.id) from EntityLeft t1 join EntityRight t2 on t1.correlation = t2.correlation", UnrelatedPair.class)
                .getResultList();
        Collections.sort(list);
        Assertions.assertEquals("[(idLeft=1,idRight=1), (idLeft=1,idRight=2), (idLeft=2,idRight=3)]", list.toString());
    }

    @Test
    public void testLeftJoin() {

        EntityLeft entityLeft1 = new EntityLeft(1, "1");
        EntityRight entityRight1 = new EntityRight(1, "1");
        EntityRight entityRight2 = new EntityRight(2, "1");

        EntityLeft entityLeft2 = new EntityLeft(2, "2");
        EntityRight entityRight3 = new EntityRight(3, "2");

        EntityLeft entityLeft3 = new EntityLeft(3, "3");
        EntityRight entityRight4 = new EntityRight(4, "4");

        EntityRight entityRight5 = new EntityRight(5, "5");

        persist(entityLeft1, entityLeft2, entityLeft3, entityRight1, entityRight2, entityRight3, entityRight4, entityRight5);

        List<UnrelatedPair> list = em.createQuery("select new relationships.unrelated.UnrelatedPair(t1.id,t2.id) from EntityLeft t1 left join EntityRight t2 on t1.correlation = t2.correlation", UnrelatedPair.class)
                .getResultList();
        Collections.sort(list);
        Assertions.assertEquals("[(idLeft=1,idRight=1), (idLeft=1,idRight=2), (idLeft=2,idRight=3), (idLeft=3,idRight=null)]", list.toString());
    }

    @Test
    public void testFullJoin() {

        EntityLeft entityLeft1 = new EntityLeft(1, "1");
        EntityRight entityRight1 = new EntityRight(1, "1");
        EntityRight entityRight2 = new EntityRight(2, "1");

        EntityLeft entityLeft2 = new EntityLeft(2, "2");
        EntityRight entityRight3 = new EntityRight(3, "2");

        EntityLeft entityLeft3 = new EntityLeft(3, "3");
        EntityRight entityRight4 = new EntityRight(4, "4");

        EntityRight entityRight5 = new EntityRight(5, "5");

        persist(entityLeft1, entityLeft2, entityLeft3, entityRight1, entityRight2, entityRight3, entityRight4, entityRight5);

        List<UnrelatedPair> list = em.createQuery("select new relationships.unrelated.UnrelatedPair(t1.id,t2.id) from EntityLeft t1 full join EntityRight t2 on t1.correlation = t2.correlation", UnrelatedPair.class)
                .getResultList();
        Collections.sort(list);
        Assertions.assertEquals("[(idLeft=null,idRight=4), (idLeft=null,idRight=5), (idLeft=1,idRight=1), (idLeft=1,idRight=2), (idLeft=2,idRight=3), (idLeft=3,idRight=null)]", list.toString());
    }

}
