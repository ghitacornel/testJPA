package queries.simple;

import entities.special.EntityWithDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestUseCurrentDateTime extends TransactionalSetup {

    Date tomorrow;

    EntityWithDate entityNow;
    EntityWithDate entityYesterday;
    EntityWithDate entityTomorrow;
    EntityWithDate entityTomorrowMinusOneHour;

    @Before
    public void before() {

        verifyCorrespondingTableIsEmpty(EntityWithDate.class);

        Date now = new Date();
        Date yesterday;
        {
            Calendar instance = Calendar.getInstance();
            instance.setTime(now);
            instance.add(Calendar.DAY_OF_YEAR, -1);
            yesterday = instance.getTime();
        }
        {
            Calendar instance = Calendar.getInstance();
            instance.setTime(now);
            instance.add(Calendar.DAY_OF_YEAR, 1);
            tomorrow = instance.getTime();
        }
        Date tomorrowMinusOneHour;
        {
            Calendar instance = Calendar.getInstance();
            instance.setTime(now);
            instance.add(Calendar.DAY_OF_YEAR, 1);
            instance.add(Calendar.HOUR, -1);
            tomorrowMinusOneHour = instance.getTime();
        }

        entityYesterday = new EntityWithDate();
        entityYesterday.setId(1);
        entityYesterday.setFullDate(yesterday);
        entityYesterday.setOnlyTime(yesterday);
        entityYesterday.setOnlyDate(yesterday);
        persist(entityYesterday);

        entityNow = new EntityWithDate();
        entityNow.setId(2);
        entityNow.setFullDate(now);
        entityNow.setOnlyTime(now);
        entityNow.setOnlyDate(now);
        persist(entityNow);

        entityTomorrow = new EntityWithDate();
        entityTomorrow.setId(3);
        entityTomorrow.setFullDate(tomorrow);
        entityTomorrow.setOnlyTime(tomorrow);
        entityTomorrow.setOnlyDate(tomorrow);
        persist(entityTomorrow);

        entityTomorrowMinusOneHour = new EntityWithDate();
        entityTomorrowMinusOneHour.setId(4);
        entityTomorrowMinusOneHour.setFullDate(tomorrowMinusOneHour);
        entityTomorrowMinusOneHour.setOnlyTime(tomorrowMinusOneHour);
        entityTomorrowMinusOneHour.setOnlyDate(tomorrowMinusOneHour);
        persist(entityTomorrowMinusOneHour);

        flushAndClear();
    }

    @Test
    public void testGetCurrent() {

        EntityWithDate existing = em.createQuery("select e from EntityWithDate e where e.onlyDate = current_date", EntityWithDate.class).getSingleResult();
        verifyEquals(entityNow, existing);

    }

    @Test
    public void testGetCurrentAndFuture() {

        List<EntityWithDate> existing = em.createQuery("select e from EntityWithDate e where e.onlyDate >= current_date order by fullDate", EntityWithDate.class).getResultList();
        Assert.assertEquals(3, existing.size());
        verifyEquals(entityNow, existing.get(0));
        verifyEquals(entityTomorrowMinusOneHour, existing.get(1));
        verifyEquals(entityTomorrow, existing.get(2));

    }

    @Test
    public void testGetOnlyFuture() {

        List<EntityWithDate> existing = em.createQuery("select e from EntityWithDate e where e.onlyDate > current_date order by fullDate", EntityWithDate.class).getResultList();
        Assert.assertEquals(2, existing.size());
        verifyEquals(entityTomorrowMinusOneHour, existing.get(0));
        verifyEquals(entityTomorrow, existing.get(1));

    }

    /**
     * observe comparison over full date and time column which filter items based on time also
     */
    @Test
    public void testGetOnlyFutureFromTomorrow() {

        List<EntityWithDate> existing = em.createQuery("select e from EntityWithDate e where e.fullDate >= ?1 order by fullDate", EntityWithDate.class).
                setParameter(1, tomorrow).
                getResultList();
        Assert.assertEquals(1, existing.size());
        verifyEquals(entityTomorrow, existing.get(0));

    }

    @Test
    public void testGetCurrentAndPast() {

        List<EntityWithDate> existing = em.createQuery("select e from EntityWithDate e where e.onlyDate <= current_date order by fullDate", EntityWithDate.class).getResultList();
        Assert.assertEquals(2, existing.size());
        verifyEquals(entityYesterday, existing.get(0));
        verifyEquals(entityNow, existing.get(1));

    }

    @Test
    public void testGetOnlyPast() {

        List<EntityWithDate> existing = em.createQuery("select e from EntityWithDate e where e.onlyDate < current_date order by fullDate", EntityWithDate.class).getResultList();
        Assert.assertEquals(1, existing.size());
        verifyEquals(entityYesterday, existing.get(0));

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
