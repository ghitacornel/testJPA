package relationships.one.to.many.strict;

import relationships.one.to.many.strict.ChildStrict;
import relationships.one.to.many.strict.ParentStrict;
import setup.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.ArrayList;
import java.util.List;

public class TestCRUD extends TransactionalSetup {

    private List<ParentStrict> model = buildModel();

    private List<ParentStrict> buildModel() {
        List<ParentStrict> list = new ArrayList<>();

        for (int i = 1; i < 3; i++) {
            ParentStrict parent = new ParentStrict();
            parent.setId(i);
            parent.setName("strict parent " + i);
            list.add(parent);
        }

        return list;
    }

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void testCRUD() {

        // assert empty
        Assert.assertTrue(em.createQuery("select t from ChildStrict t").getResultList().isEmpty());
        flushAndClear();

        // insert
        ParentStrict parentStrict = em.find(ParentStrict.class, model.get(0).getId());
        ChildStrict childStrict = new ChildStrict();
        childStrict.setId(1);
        childStrict.setName("child name");
        childStrict.setParent(parentStrict);
        em.persist(childStrict);
        flushAndClear();

        // test insert
        List<ChildStrict> list = em.createQuery("select t from ChildStrict t", ChildStrict.class).getResultList();
        Assert.assertEquals(1, list.size());
        ReflectionAssert.assertReflectionEquals(childStrict, list.get(0));
        flushAndClear();

        // update
        ChildStrict existing = em.find(ChildStrict.class, 1);
        existing.setName("new child name");
        ParentStrict newParentStrict = em.find(ParentStrict.class, model.get(1).getId());
        existing.setParent(newParentStrict);
        em.merge(existing);
        flushAndClear();

        // test update
        existing = em.find(ChildStrict.class, 1);
        Assert.assertEquals("new child name", existing.getName());
        ReflectionAssert.assertReflectionEquals(newParentStrict, existing.getParent());
        flushAndClear();

        // remove
        existing = em.find(ChildStrict.class, 1);
        Assert.assertNotNull(existing);
        em.remove(existing);
        flushAndClear();

        // test remove
        Assert.assertTrue(em.createQuery("select t from ChildStrict t").getResultList().isEmpty());

        // this test verifies cascade is not present
        // cascade removal => test fails
        ReflectionAssert.assertReflectionEquals(model, em.createQuery("select t from ParentStrict t").getResultList(), ReflectionComparatorMode.LENIENT_ORDER);
        flushAndClear();

    }
}
