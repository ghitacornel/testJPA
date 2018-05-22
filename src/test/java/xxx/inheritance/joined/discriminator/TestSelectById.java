package xxx.inheritance.joined.discriminator;

import entities.inheritance.joined.discriminator.InheritanceJoinedTablesDiscriminatorSuperClass;
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

    private List<InheritanceJoinedTablesDiscriminatorSuperClass> model = TestSelectAll.buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void testSublassA() {

        InheritanceJoinedTablesDiscriminatorSuperClass result = em.find(InheritanceJoinedTablesDiscriminatorSuperClass.class, model.get(0).getId());

        ReflectionAssert.assertReflectionEquals(model.get(0), result);
    }

    @Test
    public void testSubclassB() {

        InheritanceJoinedTablesDiscriminatorSuperClass result = em.find(InheritanceJoinedTablesDiscriminatorSuperClass.class, model.get(3).getId());

        ReflectionAssert.assertReflectionEquals(model.get(3), result);
    }

    @Test
    public void testParent() {

        InheritanceJoinedTablesDiscriminatorSuperClass result = em.find(InheritanceJoinedTablesDiscriminatorSuperClass.class, model.get(6).getId());

        ReflectionAssert.assertReflectionEquals(model.get(6), result);
    }

}
