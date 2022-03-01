package entities.special;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestEntityWithDate extends TransactionalSetup {

    @BeforeEach
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
        EntityWithDate persistedEntity = em.find(EntityWithDate.class, initialEntity.getId());
        Assertions.assertNotNull(persistedEntity);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Assertions.assertEquals(simpleDateFormat.format(referenceDate), simpleDateFormat.format(persistedEntity.getFullDate()));

        SimpleDateFormat simpleDateOnlyFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Assertions.assertEquals(simpleDateOnlyFormat.format(referenceDate), simpleDateOnlyFormat.format(persistedEntity.getOnlyDate()));

        SimpleDateFormat simpleTimeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
        Assertions.assertEquals(simpleTimeOnlyFormat.format(referenceDate), simpleTimeOnlyFormat.format(persistedEntity.getOnlyTime()));

    }
}
