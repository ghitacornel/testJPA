package queries.simple;

import org.junit.Assert;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestStringProcessing extends TransactionalSetup {

    @Test
    public void testCONCAT() {

        SimpleQueryEntity entity = new SimpleQueryEntity();
        entity.setId(1);
        entity.setName("name 1");
        entity.setValue(1);
        persist(entity);
        flushAndClear();

        Assert.assertEquals("abc name 1", em.createQuery("select concat('abc ',name) from SQE where id = 1", String.class).getSingleResult());
    }

    @Test
    public void testTRIM() {

        SimpleQueryEntity entity = new SimpleQueryEntity();
        entity.setId(1);
        entity.setName(" name ");
        entity.setValue(1);
        persist(entity);
        flushAndClear();

        Assert.assertEquals(" name ", em.createQuery("select name from SQE where id = 1", String.class).getSingleResult());
        Assert.assertEquals("name", em.createQuery("select trim(name) from SQE where id = 1", String.class).getSingleResult());
    }

    @Test
    public void testTRIM_LEADING() {

        SimpleQueryEntity entity = new SimpleQueryEntity();
        entity.setId(1);
        entity.setName(" name ");
        entity.setValue(1);
        persist(entity);
        flushAndClear();

        Assert.assertEquals(" name ", em.createQuery("select name from SQE where id = 1", String.class).getSingleResult());
        Assert.assertEquals("name ", em.createQuery("select trim(leading from name) from SQE where id = 1", String.class).getSingleResult());
    }

    @Test
    public void testTRIM_TRAILING() {

        SimpleQueryEntity entity = new SimpleQueryEntity();
        entity.setId(1);
        entity.setName(" name ");
        entity.setValue(1);
        persist(entity);
        flushAndClear();

        Assert.assertEquals(" name ", em.createQuery("select name from SQE where id = 1", String.class).getSingleResult());
        Assert.assertEquals(" name", em.createQuery("select trim(trailing from name) from SQE where id = 1", String.class).getSingleResult());
    }

    @Test// same as simple trim()
    public void testTRIM_BOTH() {

        SimpleQueryEntity entity = new SimpleQueryEntity();
        entity.setId(1);
        entity.setName(" name ");
        entity.setValue(1);
        persist(entity);
        flushAndClear();

        Assert.assertEquals(" name ", em.createQuery("select name from SQE where id = 1", String.class).getSingleResult());
        Assert.assertEquals("name", em.createQuery("select trim(both from name) from SQE where id = 1", String.class).getSingleResult());
    }

    @Test
    public void testTRIM_BOTH_CUSTOM_CHARACTER() {

        SimpleQueryEntity entity = new SimpleQueryEntity();
        entity.setId(1);
        entity.setName("%name%");
        entity.setValue(1);
        persist(entity);
        flushAndClear();

        Assert.assertEquals("%name%", em.createQuery("select name from SQE where id = 1", String.class).getSingleResult());
        Assert.assertEquals("name", em.createQuery("select trim(both '%' from name) from SQE where id = 1", String.class).getSingleResult());
    }

    @Test
    public void testLOWER() {

        SimpleQueryEntity entity = new SimpleQueryEntity();
        entity.setId(1);
        entity.setName("Name");
        entity.setValue(1);
        persist(entity);
        flushAndClear();

        Assert.assertEquals("Name", em.createQuery("select name from SQE where id = 1", String.class).getSingleResult());
        Assert.assertEquals("name", em.createQuery("select lower(name) from SQE where id = 1", String.class).getSingleResult());
    }

    @Test
    public void testUPPER() {

        SimpleQueryEntity entity = new SimpleQueryEntity();
        entity.setId(1);
        entity.setName("Name");
        entity.setValue(1);
        persist(entity);
        flushAndClear();

        Assert.assertEquals("Name", em.createQuery("select name from SQE where id = 1", String.class).getSingleResult());
        Assert.assertEquals("NAME", em.createQuery("select upper(name) from SQE where id = 1", String.class).getSingleResult());
    }

    @Test
    public void testSUBSTRING() {

        SimpleQueryEntity entity = new SimpleQueryEntity();
        entity.setId(1);
        entity.setName("Names");
        entity.setValue(1);
        persist(entity);
        flushAndClear();

        Assert.assertEquals("ame", em.createQuery("select substring(name,2,3) from SQE where id = 1", String.class).getSingleResult());
    }

    @Test
    public void testLENGTH() {

        SimpleQueryEntity entity = new SimpleQueryEntity();
        entity.setId(1);
        entity.setName("Name");
        entity.setValue(1);
        persist(entity);
        flushAndClear();

        Assert.assertEquals(new Integer(entity.getName().length()), em.createQuery("select length(name) from SQE where id = 1", Integer.class).getSingleResult());
    }

    @Test
    public void testLOCATE() {

        SimpleQueryEntity entity = new SimpleQueryEntity();
        entity.setId(1);
        entity.setName("abcabcabc");
        entity.setValue(1);
        persist(entity);
        flushAndClear();

        Assert.assertEquals(new Integer(4), em.createQuery("select locate('abc',name,2) from SQE where id = 1", Integer.class).getSingleResult());
    }

}
