package setup;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.persistence.Persistence;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class EntityManagerFactoryExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private static boolean started = false;

    // persistence unit name must match the persistence unit name specified in the persistence.xml file
    private static final String PERSISTENCE_UNIT_NAME = "examplePersistenceUnit";

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!started) {
            started = true;
            // Your "before all tests" startup logic goes here
            // The following line registers a callback hook when the root test context is shut down
            context.getRoot().getStore(GLOBAL).put("entity manager factory container", this);

            Setup.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
    }

    @Override
    public void close() {
        if (Setup.entityManagerFactory != null) {
            Setup.entityManagerFactory.close();
        }
    }
}