package queries.simple;

import entities.special.EntityWithDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestUseCurrentDateTime extends TransactionalSetup {

    EntityWithDate entityNow;
    EntityWithDate entityYesterday;
    EntityWithDate entityTomorrow;

    @Before
    public void before() {

        verifyCorrespondingTableIsEmpty(EntityWithDate.class);

        Date now = new Date();
        Date yesterday;
        {
            Calendar instance = Calendar.getInstance();
            instance.setTime(now);
            instance.add(Calendar.DAY_OF_YEAR, -1);
            instance.add(Calendar.HOUR, -1);
            instance.add(Calendar.MINUTE, -1);
            yesterday = instance.getTime();
        }
        Date tomorrow;
        {
            Calendar instance = Calendar.getInstance();
            instance.setTime(now);
            instance.add(Calendar.DAY_OF_YEAR, 1);
            instance.add(Calendar.HOUR, 1);
            instance.add(Calendar.MINUTE, 1);
            tomorrow = instance.getTime();
        }

        entityNow = new EntityWithDate();
        entityNow.setId(1);
        entityNow.setFullDate(now);
        entityNow.setOnlyTime(now);
        entityNow.setOnlyDate(now);
        persist(entityNow);

        entityYesterday = new EntityWithDate();
        entityYesterday.setId(2);
        entityYesterday.setFullDate(yesterday);
        entityYesterday.setOnlyTime(yesterday);
        entityYesterday.setOnlyDate(yesterday);
        persist(entityYesterday);

        entityTomorrow = new EntityWithDate();
        entityTomorrow.setId(3);
        entityTomorrow.setFullDate(tomorrow);
        entityTomorrow.setOnlyTime(tomorrow);
        entityTomorrow.setOnlyDate(tomorrow);
        persist(entityTomorrow);

        flushAndClear();
    }

    @Test
    public void testGetCurrentAll() {

        EntityWithDate existing = em.createQuery("select e from EntityWithDate e where e.onlyDate = current_date", EntityWithDate.class).getSingleResult();
        verifyEquals(entityNow, existing);

    }

    private void verifyEquals(EntityWithDate entity1, EntityWithDate entity2) {

        Assert.assertEquals(entity1.getId(), entity2.getId());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
        Assert.assertEquals(simpleDateFormat.format(entity1.getFullDate()), simpleDateFormat.format(entity2.getFullDate()));

        SimpleDateFormat simpleDateOnlyFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Assert.assertEquals(simpleDateOnlyFormat.format(entity1.getOnlyDate()), simpleDateOnlyFormat.format(entity2.getOnlyDate()));

        SimpleDateFormat simpleTimeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
        Assert.assertEquals(simpleTimeOnlyFormat.format(entity1.getOnlyTime()), simpleTimeOnlyFormat.format(entity2.getOnlyTime()));
    }

}
