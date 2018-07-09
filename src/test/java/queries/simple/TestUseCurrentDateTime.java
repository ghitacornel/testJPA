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

    Date now;
    Date yesterday;
    Date tomorrow;
    Date tomorrowMinusOneHour;

    EntityWithDate entityNow;
    EntityWithDate entityYesterday;
    EntityWithDate entityTomorrow;
    EntityWithDate entityTomorrowMinusOneHour;

    @Before
    public void before() throws Exception {

        verifyCorrespondingTableIsEmpty(EntityWithDate.class);

        // use a fixed date taken from the database for such tests
        {
            {// persist a dummy
                EntityWithDate dummy = new EntityWithDate();
                dummy.setId(-1);
                dummy.setName("dummy");
                dummy.setFullDate(new Date());
                dummy.setOnlyTime(new Date());
                dummy.setOnlyDate(new Date());
                persist(dummy);
                flushAndClear();
            }
            now = em.createQuery("select current_timestamp from EntityWithDate e where e.id=-1", Date.class).getSingleResult();// use the dummy to get the current database timestamp
            {// remove the dummy
                em.remove(em.find(EntityWithDate.class, -1));
                flushAndClear();
            }
        }
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
        {
            Calendar instance = Calendar.getInstance();
            instance.setTime(now);
            instance.add(Calendar.DAY_OF_YEAR, 1);
            instance.add(Calendar.HOUR, -1);
            tomorrowMinusOneHour = instance.getTime();
        }

        entityYesterday = new EntityWithDate();
        entityYesterday.setId(1);
        entityYesterday.setName("entityYesterday");
        entityYesterday.setFullDate(yesterday);
        entityYesterday.setOnlyTime(stripTime(yesterday));
        entityYesterday.setOnlyDate(yesterday);
        persist(entityYesterday);

        entityNow = new EntityWithDate();
        entityNow.setId(2);
        entityNow.setName("entityNow");
        entityNow.setFullDate(now);
        entityNow.setOnlyTime(stripTime(now));
        entityNow.setOnlyDate(now);
        persist(entityNow);

        entityTomorrow = new EntityWithDate();
        entityTomorrow.setId(3);
        entityTomorrow.setName("entityTomorrow");
        entityTomorrow.setFullDate(tomorrow);
        entityTomorrow.setOnlyTime(stripTime(tomorrow));
        entityTomorrow.setOnlyDate(tomorrow);
        persist(entityTomorrow);

        entityTomorrowMinusOneHour = new EntityWithDate();
        entityTomorrowMinusOneHour.setId(4);
        entityTomorrowMinusOneHour.setName("entityTomorrowMinusOneHour");
        entityTomorrowMinusOneHour.setFullDate(tomorrowMinusOneHour);
        entityTomorrowMinusOneHour.setOnlyTime(stripTime(tomorrowMinusOneHour));
        entityTomorrowMinusOneHour.setOnlyDate(tomorrowMinusOneHour);
        persist(entityTomorrowMinusOneHour);

        flushAndClear();
    }

    private Date stripTime(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        return instance.getTime();
    }

    @Test
    public void testGetCurrent() {

        List<EntityWithDate> existing = em.createQuery("select e from EntityWithDate e where e.onlyDate = current_date", EntityWithDate.class).getResultList();
        Assert.assertEquals(1, existing.size());
        verifyEquals(entityNow, existing.get(0));

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
