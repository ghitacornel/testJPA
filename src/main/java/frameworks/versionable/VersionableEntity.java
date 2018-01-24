package frameworks.versionable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * an entity that has versions
 * Created by Cornel on 12.07.2015.
 */
@MappedSuperclass
public abstract class VersionableEntity<T extends EntityVersion> {

    // TODO link it from the versions to the owner
    @Transient
    private List<T> versions = new ArrayList<>();

    // TODO link it from the owner to the owner version
    @Transient
    private T currentVersion;

    public T getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(T currentVersion) {
        this.currentVersion = currentVersion;
    }

    public List<T> getVersions() {
        return versions;
    }
}
