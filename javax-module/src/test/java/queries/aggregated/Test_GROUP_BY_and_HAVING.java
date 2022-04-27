package queries.aggregated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.List;

public class Test_GROUP_BY_and_HAVING extends TransactionalSetup {

    @BeforeEach
    public void ensureSomeDataIsPresent() {
        verifyCorrespondingTableIsEmpty(Person.class);
        verifyCorrespondingTableIsEmpty(Country.class);

        Country romania = new Country(1, "romania");
        em.persist(romania);
        Country france = new Country(2, "france");
        em.persist(france);
        Country italy = new Country(3, "italy");
        em.persist(italy);
        Country england = new Country(4, "england");
        em.persist(england);

        Person ion = new Person(1, "ion", romania);
        em.persist(ion);
        Person gheorghe = new Person(2, "gheorghe", romania);
        em.persist(gheorghe);
        Person marin = new Person(3, "marin", romania);
        em.persist(marin);

        Person pierre = new Person(4, "pierre", france);
        em.persist(pierre);
        Person jaques = new Person(5, "jaques", france);
        em.persist(jaques);

        Person giovanni = new Person(6, "giovanni", italy);
        em.persist(giovanni);
    }

    @Test
    public void test_GROUP_BY() {

        List<Object[]> data = em.createQuery("select c.name, count(p) from Country c left join Person p on p.countryId = c.id group by c.name order by count(p) desc").getResultList();
        Assertions.assertEquals(4, data.size());
        Object[] row0 = new Object[]{"romania", 3L};
        Object[] row1 = new Object[]{"france", 2L};
        Object[] row2 = new Object[]{"italy", 1L};
        Object[] row3 = new Object[]{"england", 0L};
        Assertions.assertArrayEquals(row0, data.get(0));
        Assertions.assertArrayEquals(row1, data.get(1));
        Assertions.assertArrayEquals(row2, data.get(2));
        Assertions.assertArrayEquals(row3, data.get(3));

    }

    @Test
    public void test_GROUP_BY_HAVING() {

        List<Object[]> data = em.createQuery("select c.name, count(p) from Person p inner join Country c on p.countryId = c.id group by c.name having count(*)>1 order by count(p) desc").getResultList();
        Assertions.assertEquals(2, data.size());
        Object[] row0 = new Object[]{"romania", 3L};
        Object[] row1 = new Object[]{"france", 2L};
        Assertions.assertArrayEquals(row0, data.get(0));
        Assertions.assertArrayEquals(row1, data.get(1));

    }
}
