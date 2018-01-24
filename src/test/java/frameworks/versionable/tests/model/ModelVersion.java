package frameworks.versionable.tests.model;

import frameworks.versionable.EntityVersion;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ModelVersion extends EntityVersion {

    @Basic(optional = false)
    @Column(name = "definition", nullable = false)
    private String definition;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
