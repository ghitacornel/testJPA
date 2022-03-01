package entities.simple;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.Setup;

public class TestMergeBehaviorWithControlledTransactions extends Setup {

    @BeforeEach
    public void before() {

        verifyCorrespondingTableIsEmpty(Entity.class);

        // make sure we have some data on a different transaction
        {
            em.getTransaction().begin();
            em.persist(buildModel());
            em.getTransaction().commit();
        }

        final Runnable r = () -> {
            System.out.println("LAUNCHING HSQL DBMANAGERSWING");
            final String[] args = { "--url", "jdbc:hsqldb:mem:testdb" ,"--noexit"};
            try {
                // enable database GUI from here
                // DatabaseManagerSwing.main(args);
            } catch (final Exception e) {
                System.out.println("Could not start hsqldb database manager GUI: " + e.getMessage());
            }
        };
        new Thread(r).start();

    }

    /**
     * make sure we clean the database on a different transaction
     */
    @AfterEach
    public void after() {
        em.getTransaction().begin();
        em.createQuery("delete from Entity t").executeUpdate();
        em.getTransaction().commit();
    }

    private Entity buildModel() {
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");
        return entity;
    }

    @Test
    public void test_MergeANotManagedEntity_AndObserve_ManagedVsNotManagedEntitiesBehavior() {

        // witness object
        Entity witness = buildModel();

        // begin transaction
        em.getTransaction().begin();

        // update model
        Entity detachedEntity = buildModel();// original updated model is detached
        detachedEntity.setName("initial name");

        // merge model to the database
        Entity attachedEntity = em.merge(detachedEntity);
        em.flush();

        // a second object is created and attached to the persistence context
        // since the original updated model is detached
        Assertions.assertNotSame(attachedEntity, detachedEntity);

        // make further changes on the not managed instance
        detachedEntity.setName("still detached name");

        // make further changes on the managed instance
        attachedEntity.setName("managed instance name");

        // commit at the end
        em.getTransaction().commit();

        // verify on a different transaction that the merge worked with the attached entity and not the detached entity
        {
            em.getTransaction().begin();
            Entity updated = em.find(Entity.class, witness.getId());
            Assertions.assertNotNull(updated);
            ReflectionAssert.assertReflectionEquals(attachedEntity, updated);
            em.getTransaction().commit();
        }

    }

    @Test
    public void test_MergeAnAlreadyManagedEntity_AndObserve_NoNewManagedEntitiesAreReturnedByTheMergeOperation() {

        // witness object
        Entity witness = buildModel();

        // begin transaction
        em.getTransaction().begin();

        // update model
        Entity attachedEntity = em.find(Entity.class, witness.getId());
        attachedEntity.setName("newName1");

        // merge model to the database
        Entity mergedEntity = em.merge(attachedEntity);

        // a second object is not created since the original updated model is fetched first
        // hence already attached to the persistence context
        Assertions.assertSame(mergedEntity, attachedEntity);

        // make further changes on the not managed instance
        mergedEntity.setName("newName2");

        // commit at the end
        em.getTransaction().commit();

        // verify on a different transaction that the merge worked
        {
            em.getTransaction().begin();
            Entity updated = em.find(Entity.class, witness.getId());
            Assertions.assertNotNull(updated);
            ReflectionAssert.assertReflectionEquals(mergedEntity, updated);
            ReflectionAssert.assertReflectionEquals(attachedEntity, updated);
            ReflectionAssert.assertReflectionEquals(mergedEntity, attachedEntity);
            em.getTransaction().commit();
        }

    }

}
