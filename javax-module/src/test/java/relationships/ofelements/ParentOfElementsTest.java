package relationships.ofelements;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.HashSet;
import java.util.List;

public class ParentOfElementsTest extends TransactionalSetup {

    @Test
    public void testJoinFetchLikeOnElements() {


        ParentOfElements parent = new ParentOfElements();
        {
            parent.setId(1);
            parent.setName("parent of elements");
            parent.setElements(new HashSet<>());
            parent.getElements().add("one");
            parent.getElements().add("ones");
            parent.getElements().add("two");
            parent.getElements().add("twice");
            parent.getElements().add("trice");
            em.persist(parent);
        }

        {
            ParentOfElements notMatched = new ParentOfElements();
            notMatched.setId(2);
            notMatched.setName("parent of elements not matched");
            notMatched.setElements(new HashSet<>());
            notMatched.getElements().add("1");
            notMatched.getElements().add("twos");
            em.persist(notMatched);
        }

        em.persist(parent);

        String query = "select p from ParentOfElements p join p.elements e where e like :name";

        List<ParentOfElements> parentOfElements = em.createQuery(query, ParentOfElements.class)
                .setParameter("name", "two")
                .getResultList();
        Assertions.assertEquals(1, parentOfElements.size());
        Assertions.assertEquals(parent.getId(), parentOfElements.get(0).getId());

    }
}
