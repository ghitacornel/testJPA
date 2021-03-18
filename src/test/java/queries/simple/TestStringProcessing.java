package queries.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestStringProcessing extends TransactionalSetup {

    SimpleQueryEntity entity1;
    SimpleQueryEntity entity2;
    SimpleQueryEntity entity3;
    SimpleQueryEntity entity4;
    SimpleQueryEntity entity5;
    SimpleQueryEntity entity6;

    @Before
    public void addData(){

        entity1 = new SimpleQueryEntity();
        entity1.setId(1);
        entity1.setName("name 1");
        entity1.setValue(1);

        entity2 = new SimpleQueryEntity();
        entity2.setId(2);
        entity2.setName(" name ");
        entity2.setValue(2);

        entity3 = new SimpleQueryEntity();
        entity3.setId(3);
        entity3.setName("%name%");
        entity3.setValue(3);

        entity4 = new SimpleQueryEntity();
        entity4.setId(4);
        entity4.setName("Name");
        entity4.setValue(4);

        entity5 = new SimpleQueryEntity();
        entity5.setId(5);
        entity5.setName("Names");
        entity5.setValue(5);

        entity6 = new SimpleQueryEntity();
        entity6.setId(6);
        entity6.setName("abcabcabc");
        entity6.setValue(6);

        persist(entity1);
        persist(entity2);
        persist(entity3);
        persist(entity4);
        persist(entity5);
        persist(entity6);
        flushAndClear();

    }

    @Test
    public void testCONCAT() {
        Assert.assertEquals("abc name 1", em.createQuery("select concat('abc ',name) from SQE where id = 1", String.class).getSingleResult());
    }

    @Test
    public void testTRIM() {
        Assert.assertEquals(" name ", em.createQuery("select name from SQE where id = 2", String.class).getSingleResult());
        Assert.assertEquals("name", em.createQuery("select trim(name) from SQE where id = 2", String.class).getSingleResult());
    }

    @Test
    public void testTRIM_LEADING() {
        Assert.assertEquals(" name ", em.createQuery("select name from SQE where id = 2", String.class).getSingleResult());
        Assert.assertEquals("name ", em.createQuery("select trim(leading from name) from SQE where id = 2", String.class).getSingleResult());
    }

    @Test
    public void testTRIM_TRAILING() {
        Assert.assertEquals(" name ", em.createQuery("select name from SQE where id = 2", String.class).getSingleResult());
        Assert.assertEquals(" name", em.createQuery("select trim(trailing from name) from SQE where id = 2", String.class).getSingleResult());
    }

    @Test// same as simple trim()
    public void testTRIM_BOTH() {
        Assert.assertEquals(" name ", em.createQuery("select name from SQE where id = 2", String.class).getSingleResult());
        Assert.assertEquals("name", em.createQuery("select trim(both from name) from SQE where id = 2", String.class).getSingleResult());
    }

    @Test
    public void testTRIM_BOTH_CUSTOM_CHARACTER() {
        Assert.assertEquals("%name%", em.createQuery("select name from SQE where id = 3", String.class).getSingleResult());
        Assert.assertEquals("name", em.createQuery("select trim(both '%' from name) from SQE where id = 3", String.class).getSingleResult());
    }

    @Test
    public void testLOWER() {
        Assert.assertEquals("Name", em.createQuery("select name from SQE where id = 4", String.class).getSingleResult());
        Assert.assertEquals("name", em.createQuery("select lower(name) from SQE where id = 4", String.class).getSingleResult());
    }

    @Test
    public void testUPPER() {
        Assert.assertEquals("Name", em.createQuery("select name from SQE where id = 4", String.class).getSingleResult());
        Assert.assertEquals("NAME", em.createQuery("select upper(name) from SQE where id = 4", String.class).getSingleResult());
    }

    @Test
    public void testSUBSTRING() {
        Assert.assertEquals("ame", em.createQuery("select substring(name,2,3) from SQE where id = 5", String.class).getSingleResult());
    }

    @Test
    public void testLENGTH() {
        Assert.assertEquals(Integer.valueOf(entity4.getName().length()), em.createQuery("select length(name) from SQE where id = 4", Integer.class).getSingleResult());
    }

    @Test
    public void testLOCATE() {
        Assert.assertEquals(Integer.valueOf(4), em.createQuery("select locate('abc',name,2) from SQE where id = 6", Integer.class).getSingleResult());
    }

}
