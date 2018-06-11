package relationships.manytomany.nocascade;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * WARNING 1 JPA {@link Query} doesn't map always to 1 SQL query as it's the
 * case here
 *
 * @author Cornel
 */
public class TestSelectSingleMWithRelatedNInOneQuery extends TransactionalSetup {

    private List<Object> model = TestSelectAll.buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        NoCascadeM m = em.createQuery("select t from NoCascadeM t join fetch t.listWithNs where t.id=1", NoCascadeM.class).getSingleResult();

        Assert.assertNotNull(m);
        ReflectionAssert.assertReflectionEquals(model.get(4), m);
        List<NoCascadeN> expected = new ArrayList<>();
        expected.add((NoCascadeN) model.get(0));
        ReflectionAssert.assertReflectionEquals(expected, m.getListWithNs(), ReflectionComparatorMode.LENIENT_ORDER);
    }
}
