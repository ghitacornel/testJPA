package entities.aggregated;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.util.List;

public class Test_GROUP_BY_and_HAVING extends TransactionalSetup {


    @Before
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
    public void test() {

        List<Object[]> data = em.createQuery("select c.name,count(*) from Person p inner join Country c on p.countryId = c.id group by c.name having count(*)>1 order by count(*) desc").getResultList();
        Assert.assertEquals(2, data.size());
        Object[] row0 = new Object[]{"romania", 3L};
        Object[] row1 = new Object[]{"france", 2L};
        Assert.assertArrayEquals(row0, data.get(0));
        Assert.assertArrayEquals(row1, data.get(1));

    }
}
