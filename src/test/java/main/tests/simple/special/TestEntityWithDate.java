package main.tests.simple.special;

import entities.simple.special.EntityWithDate;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestEntityWithDate extends TransactionalSetup {

    @Before
    public void before() {
        Assert.assertTrue(em.createQuery("select t from EntityWithDate t").getResultList().isEmpty());
    }

    @Test
    public void test() {

        // set a reference date
        Date referenceDate = new Date();

        // create new entity
        EntityWithDate entity1 = new EntityWithDate();
        entity1.setId(1);
        entity1.setFullDate(referenceDate);
        entity1.setOnlyDate(referenceDate);
        entity1.setOnlyTime(referenceDate);

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify persist
        EntityWithDate entity2 = em.find(EntityWithDate.class, 1);
        Assert.assertNotNull(entity2);
        Assert.assertEquals(1, (int) entity2.getId());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
        Assert.assertEquals(simpleDateFormat.format(referenceDate), simpleDateFormat.format(entity2.getFullDate()));

        SimpleDateFormat simpleDateOnlyFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Assert.assertEquals(simpleDateOnlyFormat.format(referenceDate), simpleDateOnlyFormat.format(entity2.getOnlyDate()));

        SimpleDateFormat simpleTimeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
        Assert.assertEquals(simpleTimeOnlyFormat.format(referenceDate), simpleTimeOnlyFormat.format(entity2.getOnlyTime()));

    }
}
