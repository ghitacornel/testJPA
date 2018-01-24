package main.tests.inheritance.joined;

import entities.inheritance.joined.InheritanceJoinedTablesSuperClass;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.List;

/**
 * observe that additional SQL joins are performed
 *
 * @author Cornel
 */
public class TestSelectById extends TransactionalSetup {

    private List<InheritanceJoinedTablesSuperClass> model = TestSelectAll.buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void testSubclassA() {

        InheritanceJoinedTablesSuperClass result = em.find(InheritanceJoinedTablesSuperClass.class, model.get(0).getId());

        ReflectionAssert.assertReflectionEquals(model.get(0), result);
    }

    @Test
    public void testSubclassB() {

        InheritanceJoinedTablesSuperClass result = em.find(InheritanceJoinedTablesSuperClass.class, model.get(3).getId());

        ReflectionAssert.assertReflectionEquals(model.get(3), result);
    }

    @Test
    public void testParent() {

        InheritanceJoinedTablesSuperClass result = em.find(InheritanceJoinedTablesSuperClass.class, model.get(6).getId());

        ReflectionAssert.assertReflectionEquals(model.get(6), result);
    }

}
