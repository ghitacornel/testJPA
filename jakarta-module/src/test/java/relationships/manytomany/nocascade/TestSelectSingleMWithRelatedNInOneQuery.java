package relationships.manytomany.nocascade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * WARNING 1 JPA {@link Query} doesn't map always to 1 SQL query as it is the case here
 */
public class TestSelectSingleMWithRelatedNInOneQuery extends TransactionalSetup {

    private List<Object> model = TestSelectAll.buildModel();

    @BeforeEach
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        NoCascadeM m = em.createQuery("select t from NoCascadeM t join fetch t.listWithNs where t.id=1", NoCascadeM.class).getSingleResult();

        Assertions.assertNotNull(m);
        org.assertj.core.api.Assertions.assertThat(model.get(4)).usingRecursiveComparison().isEqualTo(m);
        List<NoCascadeN> expected = new ArrayList<>();
        expected.add((NoCascadeN) model.get(0));
        org.assertj.core.api.Assertions.assertThat(expected).usingRecursiveComparison().isEqualTo(m.getListWithNs());
    }
}
