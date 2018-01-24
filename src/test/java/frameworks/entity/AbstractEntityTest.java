package frameworks.entity;

import org.junit.Assert;
import org.junit.Test;

public class AbstractEntityTest {

    @Test
    public void testToStringNullId() {
        A a = new A();
        Assert.assertEquals(A.class.getSimpleName() + "[id=null]", a.toString());
    }

    @Test
    public void testToStringNotNullId() {
        A a = new A();
        a.id = 1L;
        Assert.assertEquals(A.class.getSimpleName() + "[id=1]", a.toString());
    }

    @Test
    public void testHashCodeNullId() {
        A a = new A();
        Assert.assertEquals(A.class.hashCode(), a.hashCode());
    }

    @Test
    public void testHashCodeNotNullId() {
        A a = new A();
        a.id = 11L;
        Assert.assertEquals(a.id.hashCode(), a.hashCode());
    }

    @Test
    public void testEqualsSameObject() {
        A a = new A();
        Assert.assertEquals(a, a);
    }

    @Test
    public void testEqualsNullObject() {
        Assert.assertNotEquals(new A(), null);
    }

    @Test
    public void testEqualsNullIds() {
        Assert.assertNotEquals(new A(), new A());
    }

    @Test
    public void testEqualsDifferentClasses() {
        A a = new A();
        B b = new B();
        a.id = 1L;
        b.id = 1L;
        Assert.assertNotEquals(a, b);
    }

    @Test
    public void testEqualsSameClassSameNotNullId() {

        A a1 = new A();
        a1.id = 1L;

        A a2 = new A();
        a2.id = 1L;

        Assert.assertEquals(a1, a2);

    }

    @Test
    public void testEqualsSameClassDifferentNotNullId() {

        A a1 = new A();
        a1.id = 1L;

        A a2 = new A();
        a2.id = 2L;

        Assert.assertNotEquals(a1, a2);

    }

    private static class A extends AbstractEntity<Long> {

        protected Long id;

        @Override
        public Long getId() {
            return id;
        }
    }

    private static class B extends A {
    }
}
