package entities.special;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestEntityWithDate extends TransactionalSetup {

    @Before
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithDate.class);
    }

    @Test
    public void test() {

        // set a reference date
        Date referenceDate = new Date();

        // create new entity
        EntityWithDate initialEntity = new EntityWithDate();
        initialEntity.setId(1);
        initialEntity.setFullDate(referenceDate);
        initialEntity.setOnlyDate(referenceDate);
        initialEntity.setOnlyTime(referenceDate);

        // persist
        em.persist(initialEntity);
        flushAndClear();

        // verify persist
        EntityWithDate persistedEntity = em.find(EntityWithDate.class, 1);
        Assert.assertNotNull(persistedEntity);
        Assert.assertEquals(1, (int) persistedEntity.getId());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
        Assert.assertEquals(simpleDateFormat.format(referenceDate), simpleDateFormat.format(persistedEntity.getFullDate()));

        SimpleDateFormat simpleDateOnlyFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Assert.assertEquals(simpleDateOnlyFormat.format(referenceDate), simpleDateOnlyFormat.format(persistedEntity.getOnlyDate()));

        SimpleDateFormat simpleTimeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
        Assert.assertEquals(simpleTimeOnlyFormat.format(referenceDate), simpleTimeOnlyFormat.format(persistedEntity.getOnlyTime()));

    }
}
