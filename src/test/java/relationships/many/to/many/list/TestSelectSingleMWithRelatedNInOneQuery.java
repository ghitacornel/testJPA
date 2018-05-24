package relationships.many.to.many.list;

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

        M m = em.createQuery("select t from M t join fetch t.listWithNs where t.id=1", M.class).getSingleResult();

        Assert.assertNotNull(m);
        ReflectionAssert.assertReflectionEquals(model.get(4), m);
        List<N> expected = new ArrayList<>();
        expected.add((N) model.get(0));
        ReflectionAssert.assertReflectionEquals(expected, m.getListWithNs(), ReflectionComparatorMode.LENIENT_ORDER);
    }
}
